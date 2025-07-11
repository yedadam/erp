package com.dadam.subscribe.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.dadam.security.service.EmployeesVO;
import com.dadam.security.service.LoginMainAuthority;
import com.dadam.subscribe.mapper.SubscribeMapper;
import com.dadam.subscribe.service.SubsListVO;
import com.dadam.subscribe.service.SubscribeService;
import com.dadam.subscribe.service.SubscribeVO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 신현욱
 * @since 2025.06.18
 * @desc 구독 관련 서비스 구현체
 *  - 구독 메뉴 조회, 중복 확인, 구독 등록 처리
 *  - 아임포트 API 연동을 통한 토큰 발급 및 정기결제 처리 기능 포함
 * @history
 *   - 2025.06.18 신현욱: 아임포트 토큰 발급 기능 추가
 *   - 2025.06.21 신현욱: 정기결제 재청구 기능 및 스케줄러 로직 추가
 */
@Service
public class SubscirbeServiceImple implements SubscribeService {

    @Autowired
    SubscribeMapper subsMapper;

    @Value("${portone.imp_key}")
    private String impKey;

    @Value("${portone.imp_secret}")
    private String impSecret;

    private final String BASE_URL = "https://api.iamport.kr";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @desc 구독 메뉴 리스트 조회
     * @return List<SubscribeVO> 구독 메뉴 목록 반환
     */
    public List<SubscribeVO> menuList() {
        return subsMapper.menuList();
    }

    /**
     * @desc 구독 중복 체크 (사업자번호 등 중복 여부 판단)
     * @param param 중복 체크 대상 문자열
     * @return int 중복 여부 (0<: 중복 있음, 0: 중복 없음)
     */
    @Override
    public int sameCheck(String param) {
        return subsMapper.sameCheck(param);
    }

    /**
     * @desc 구독 등록 및 세금계산서 등록 처리
     * @param param 구독 정보 (SubsListVO)
     * @return int 처리 결과 (1 이상 성공)
     * @transactional 트랜잭션 처리 보장
     * @implNote
     *  - optionCode를 실제 DB용 코드로 변환 후 처리
     *  - 로그인 사용자 정보에서 회사명을 가져와 employees 테이블에 등록
     */
    @Transactional
    @Override
    public int subsAdd(SubsListVO param) {
        // optionCode 변환
        switch (param.getOptionCode()) {
            case "Lite": param.setOptionCode("oc-101"); break;
            case "Standard": param.setOptionCode("oc-102"); break;
            case "Premium": param.setOptionCode("oc-103"); break;
            default: break;
        }

        System.out.println("optionCode 변환 결과: " + param.getOptionCode());

        // 구독권 등록
        int result = subsMapper.subsAdd(param);
        // 세금계산서 등록
        result = subsMapper.taxAdd(param);

        // 로그인 사용자 정보 조회
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        String comName = null;
        if (principal instanceof LoginMainAuthority) {
            LoginMainAuthority user = (LoginMainAuthority) principal;
            comName = user.getMainName();
            System.out.println("회사명: " + comName);
        }

        EmployeesVO info = new EmployeesVO();
        info.setComId(param.getComId());
        info.setEmpName(comName);

        // 구독 등록 성공 시 employees 테이블에 사원 등록
        if (result > 0) {
            subsMapper.firstAdd(info);
        }

        return result;
    }

    /**
     * @desc 아임포트 API를 통해 access token 발급
     * @return String access token
     * @throws RuntimeException 토큰 발급 실패 시 예외 발생
     */
    public String getAccessToken() {
        String url = BASE_URL + "/users/getToken";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("imp_key", impKey);
        requestBody.put("imp_secret", impSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            String jsonBody = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseData = (Map<String, Object>) response.getBody().get("response");
                if (responseData != null) {
                    return (String) responseData.get("access_token");
                }
            }

            throw new RuntimeException("token 발급 실패: " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("실패", e);
        }
    }

    /**
     * @desc 정기결제 구독 리스트 조회 및 결제 재청구 처리
     * @implNote
     *  - 구독 건마다 재청구 API 호출, 만기일 업데이트, 구독 및 세금계산서 재등록 수행
     */
   // @Scheduled (fixedDelay = 3000)
    public void subsCriptionList() {
        List<SubsListVO> subs = subsMapper.selectSubs();

        subs.forEach(sub -> {
            chargeSubscription(sub);
            sub.setSubsType("S01");
            // 만기일자 업데이트
            //subsMapper.subsEnd(sub.getBillingKey());
            // 다시 결제 진행
            subsMapper.subsAdd(sub);
            // 세금계산서 발행
            subsMapper.taxAdd(sub);
        });
    }

    /**
     * @desc 아임포트 정기결제 재청구 API 호출
     * @param sub 결제 대상 구독 정보
     * @implNote merchant_uid에 timestamp를 붙여 고유값 생성
     */
    public void chargeSubscription(SubsListVO sub) {
        System.out.println("재청구 대상 구독: " + sub);
        String accessToken = getAccessToken();
        String url = BASE_URL + "/subscribe/payments/again";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("customer_uid", sub.getBillingKey());
        requestBody.put("merchant_uid", "sub_" + System.currentTimeMillis());
        requestBody.put("amount", sub.getSubsPrice());
        requestBody.put("name", "정기구독료");

        try {
            String jsonBody = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            System.out.println("재청구 응답: " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
