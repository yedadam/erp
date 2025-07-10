package com.dadam.subscribe.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.subscribe.service.ErpUsersVO;
import com.dadam.subscribe.service.SubsListVO;
import com.dadam.subscribe.service.SubsManagerService;

/** 
 * @author 신현욱
 * @since 2025.06.21
 * @desc 유저 구독 정보 관리하는 REST 컨트롤러 입니다
 * @history
 *   - 2025.06.21 신현욱 : 최초 작성, 조회 기능 구현함
 *   - 2025.06.22 신현욱 : 구독 정보 등록, 수정, 삭제 기능 추가 작업중
 */
@RestController
@RequestMapping("/main")
public class SubsManagerRestController {

    @Autowired
    SubsManagerService service;

    /**
     * @desc 전체 ERP 유저 목록 조회
     * @return List<ErpUsersVO> ERP 유저 리스트 리턴함
     */
    @GetMapping("/userList")
    public List<ErpUsersVO> userList(@RequestParam Map<String,Object> map) {
        List<ErpUsersVO> result = service.erpUserList(map);
        return result;
    }

    /**
     * @desc 특정 회사(comId) 의 구독 상세 내역 조회
     * @param param 회사ID (comId)
     * @return List<SubsListVO> 구독 상세내역 리스트
     */
    @GetMapping("/subsDetailList")
    public List<SubsListVO> subsList(@RequestParam String param) {
        List<SubsListVO> result = service.subsList(param);
        return result;
    }

    /**
     * @desc 구독 정보 수정
     * @param comId URL 경로에서 받은 회사ID
     * @param info 수정할 유저 정보
     * @return int 성공하면 1, 실패시 0 리턴
     */
    @PutMapping("/subsModify/{comId}")
    public int subsModify(@PathVariable String comId, @RequestBody ErpUsersVO info) {
        int result = service.managerUpdate(info);
        return result;
    }
}
