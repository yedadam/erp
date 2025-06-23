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

@Service
public class SubscirbeServiceImple implements SubscribeService{
	@Autowired
	SubscribeMapper subsMapper;
	public List<SubscribeVO> menuList(){
		return subsMapper.menuList();
	};
	
	//중복확인
	@Override
	public int sameCheck(String param) {
		int r = subsMapper.sameCheck(param);
		return r;
	};
	//첫결제 구독 내용 DB 저장
	@Transactional
	@Override
	public int subsAdd(SubsListVO param) {
		//해당 값에 맞게 optionCode 변경
		if(param.getOptionCode().equals("Lite")) {
		 param.setOptionCode("oc-101");
		}else if (param.getOptionCode().equals("Standard")) {
		 param.setOptionCode("oc-102"); 
		}else if(param.getOptionCode().equals("Premium")) {
		 param.setOptionCode("oc-103");	
		}
		
		System.out.println(param.getOptionCode());
		//등록
	    int result = subsMapper.subsAdd(param);
	    
	    
	    //로그인 객체값 연결
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // 로그인 객체 가져오기
        Object principal = auth.getPrincipal();
        
        //comName 가져오기
        String comName = null;
        //authority가 있을시 작동
        if (principal instanceof LoginMainAuthority) {
            LoginMainAuthority user = (LoginMainAuthority) principal;
            
            // 여기서 comName 꺼내기 (ErpUserVO에서 getter 추가 필요)
            comName = user.getMainName();
            System.out.println("회사명: " + comName);
        }
	    
	    //파라미터 보내줄 객체값 가져오기
	    EmployeesVO info = new EmployeesVO();
	    //객체값 수정
	    System.out.println("컴네임");
	    System.out.println(comName);
	    info.setComId(param.getComId());
	    info.setEmpName(comName);
	    //erpSubs에 등록이 되었다면 employees 테이블에도 추가
	    if(result > 0) {
	    //사원 등록시키기
	    subsMapper.firstAdd(info);
	    }
		return result;
	}
	
	//정기결제 스케줄러
	
	
	 @Value("${portone.imp_key}") 
	 private String impKey;
	 
	 @Value("${portone.imp_secret}")
	 private String impSecret;
	 
    String BASE_URL = "https://api.iamport.kr";
    @Autowired
    private RestTemplate restTemplate; 
    @Autowired
    private ObjectMapper objectMapper; // com.fasterxml.jackson.databind.ObjectMapper
    
    //토근발급
    public String getAccessToken() {
    	  String url = BASE_URL + "/users/getToken";

    	    Map<String, String> requestBody = new HashMap<>();
    	    requestBody.put("imp_key", impKey);
    	    requestBody.put("imp_secret", impSecret);

    	    HttpHeaders headers = new HttpHeaders();
    	    headers.setContentType(MediaType.APPLICATION_JSON); 

    	    try {
    	        String jsonBody = objectMapper.writeValueAsString(requestBody); // <-- 여기서 JSON 변환

    	        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

    	        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

    	        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
    	            Map<String, Object> responseData = (Map<String, Object>) response.getBody().get("response");
    	            if (responseData != null) {
    	                return (String) responseData.get("access_token");
    	            }
    	        }
    	        throw new RuntimeException("Access token 발급 실패: " + response.getBody());
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        throw new RuntimeException("JSON 변환 실패 또는 요청 실패", e);
    	    }
    }
    
  //정기결제 조회
  public void subsCriptionList() {
	  List<SubsListVO> subs = subsMapper.selectSubs();
	  
	  subs.forEach(sub -> {
		  chargeSubscription(sub);
		  sub.setSubsType("S01");
		  //만기일자 업데이트
		  subsMapper.subsEnd(sub.getBillingKey());
		  //다시 결제 진행
		  subsMapper.subsAdd(sub);
	  });
  }
  
  //정기결제 실행
    public void chargeSubscription(SubsListVO sub) {
    	System.out.println(sub);
    	String accessToken = getAccessToken();
        String url = BASE_URL + "/subscribe/payments/again";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Map 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("customer_uid", sub.getBillingKey());
        requestBody.put("merchant_uid", "sub_" + System.currentTimeMillis());
        requestBody.put("amount", sub.getSubsPrice());
        requestBody.put("name", "정기구독료");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(requestBody);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            System.out.println(response.getBody());
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
}
