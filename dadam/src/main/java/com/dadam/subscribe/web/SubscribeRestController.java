package com.dadam.subscribe.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.subscribe.service.SubsListVO;
import com.dadam.subscribe.service.SubscribeService;

/**
 * @author 신현욱
 * @since 2025.06.27
 * @desc 구독 등록이랑 중복 체크 기능 처리하는 레스트컨트롤러임
 * @history
 *   - 2025.06.21 신현욱 : 최초 작성
 */
@RestController
@RequestMapping("/main")
public class SubscribeRestController {

    @Autowired
    SubscribeService service;

    /**
     * @desc 구독 정보 등록
     * @param info 등록할 구독 정보 객체
     * @return int 1이면 성공 0이면 실패
     */
    @PostMapping("/billingSave")
    public int billingSave(@RequestBody SubsListVO info) {
        int r = service.subsAdd(info);
        return r;
    }

    /**
     * @desc 중복 체크 요청 처리
     * @param param 중복 체크 대상 값
     * @return int 0보다 크면 중복 있음 0이면 중복 없음
     */
    @GetMapping("/sameCheck")
    public int sameCheck(@RequestParam String param) {
        System.out.println("파람" + param);
        int r = service.sameCheck(param);
        return r;
    }
}
