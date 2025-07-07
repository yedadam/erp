package com.dadam.hr.attendance.web;

import com.dadam.hr.attendance.service.AttendanceIpVO;
import com.dadam.hr.attendance.service.AttendanceIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 근태 보안 관리 REST 컨트롤러
 */
@RestController
@RequestMapping("/erp/hr")
public class AttendanceSecurityRestController {

    /** IP 관리 서비스 */
    @Autowired
    private AttendanceIpService attendanceIpService;

    /**
     * IP 목록 조회
     * @param comId - 회사ID
     * @param type - 검색타입
     * @param value - 검색값
     * @return IP 리스트
     */
    @GetMapping("/attendanceIpList")
    public List<AttendanceIpVO> getIpList(@RequestParam String comId,
                                         @RequestParam(required = false) String type,
                                         @RequestParam(required = false) String value) {
        return attendanceIpService.findIpList(comId, type, value);
    }

    /**
     * IP 등록
     * @param vo - IP 정보
     * @return 등록 결과
     */
    @PostMapping("/insertAttendanceIp")
    public String insertIp(@RequestBody AttendanceIpVO vo) {
        try {
            attendanceIpService.insertIp(vo);
            return "IP 등록 완료";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * IP 수정
     * @param vo - IP 정보
     * @return 수정 결과
     */
    @PostMapping("/updateAttendanceIp")
    public String updateIp(@RequestBody AttendanceIpVO vo) {
        try {
            attendanceIpService.updateIp(vo);
            return "IP 수정 완료";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * IP 삭제
     * @param ipCode - IP 관리코드
     * @param comId - 회사ID
     * @return 삭제 결과
     */
    @PostMapping("/deleteAttendanceIp")
    public String deleteIp(@RequestParam String ipCode, @RequestParam String comId) {
        try {
            attendanceIpService.deleteIp(ipCode, comId);
            return "IP 삭제 완료";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
} 