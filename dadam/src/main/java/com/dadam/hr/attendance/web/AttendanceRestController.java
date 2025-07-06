package com.dadam.hr.attendance.web;

import com.dadam.hr.attendance.service.AttendanceService;
import com.dadam.hr.attendance.service.AttendanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.dadam.security.service.LoginUserAuthority;
import com.dadam.security.service.LoginMainAuthority;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 근태 관리 REST 컨트롤러
 * - 권한별 기능 제어: 출퇴근 등록은 본인만, 조회는 권한별 필터링
 * - 보안 검증: IP, GPS, 지점 검증
 * - 실시간 현황: 실시간 출퇴근 현황 조회
 */
@RestController
@RequestMapping("/erp/hr")
public class AttendanceRestController {

    /** 근태 서비스 */
    @Autowired
    private AttendanceService attendanceService;

    /**
     * 현재 사용자의 권한 정보를 가져오는 메서드
     * @return 권한 정보 (comId, deptCode, authority, empId)
     */
    private Map<String, String> getCurrentUserInfo() {
        Map<String, String> userInfo = new java.util.HashMap<>();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        if (principal instanceof LoginUserAuthority) {
            LoginUserAuthority user = (LoginUserAuthority) principal;
            userInfo.put("comId", user.getComId());
            userInfo.put("deptCode", user.getDeptCode());
            userInfo.put("authority", user.getOptionCode());
            userInfo.put("empId", user.getUserId());
        } else if (principal instanceof LoginMainAuthority) {
            LoginMainAuthority user = (LoginMainAuthority) principal;
            userInfo.put("comId", user.getComId());
            userInfo.put("deptCode", user.getDeptCode());
            userInfo.put("authority", user.getAuthority());
            userInfo.put("empId", "");
        }
        
        return userInfo;
    }

    /**
     * 관리자 권한 확인
     * @return 관리자 여부
     */
    private boolean isAdmin() {
        Map<String, String> userInfo = getCurrentUserInfo();
        String authority = userInfo.get("authority");
        return "admin".equals(authority) || "master".equals(authority);
    }

    /**
     * 출근 등록 (보안 검증 포함)
     * @param vo - 근태 정보
     * @param request - 요청 객체
     * @return 처리 결과
     */
    @PostMapping("/attendanceCheckIn")
    public String checkIn(@RequestBody AttendanceVO vo, HttpServletRequest request) {
        try {
            Map<String, String> userInfo = getCurrentUserInfo();
            
            // 본인만 출근 등록 가능
            if (!vo.getEmpId().equals(userInfo.get("empId"))) {
                return "unauthorized";
            }
            
            String ip = request.getRemoteAddr();
            String gpsInfo = request.getHeader("GPS-Info");
            String locationInfo = request.getHeader("Location-Info");
            
            String result = attendanceService.checkIn(vo, ip, gpsInfo, locationInfo);
            return result;
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    /**
     * 퇴근 등록 (보안 검증 포함)
     * @param vo - 근태 정보
     * @param request - 요청 객체
     * @return 처리 결과
     */
    @PostMapping("/attendanceCheckOut")
    public String checkOut(@RequestBody AttendanceVO vo, HttpServletRequest request) {
        try {
            Map<String, String> userInfo = getCurrentUserInfo();
            
            // 본인만 퇴근 등록 가능
            if (!vo.getEmpId().equals(userInfo.get("empId"))) {
                return "unauthorized";
            }
            
            String ip = request.getRemoteAddr();
            String gpsInfo = request.getHeader("GPS-Info");
            String locationInfo = request.getHeader("Location-Info");
            
            String result = attendanceService.checkOut(vo, ip, gpsInfo, locationInfo);
            return result;
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    /**
     * 근태 목록 조회 (권한별 필터링)
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @param fromDate - 시작일
     * @param toDate - 종료일
     * @return 근태 리스트
     */
    @GetMapping("/attendanceList")
    public List<AttendanceVO> getAttendanceList(@RequestParam String comId, @RequestParam(required = false) String empId,
                                                @RequestParam String fromDate, @RequestParam String toDate) {
        Map<String, String> userInfo = getCurrentUserInfo();
        String deptCode = userInfo.get("deptCode");
        
        // 관리자가 아닌 경우 본인 부서만 조회 가능
        if (!isAdmin()) {
            deptCode = userInfo.get("deptCode");
        }
        
        return attendanceService.getAttendanceList(comId, empId, fromDate, toDate, deptCode);
    }

    /**
     * 실시간 출퇴근 현황 조회 (권한별 필터링)
     * @param comId - 회사ID
     * @return 실시간 현황
     */
    @GetMapping("/realtimeStatus")
    public List<AttendanceVO> getRealtimeStatus(@RequestParam String comId) {
        Map<String, String> userInfo = getCurrentUserInfo();
        String deptCode = userInfo.get("deptCode");
        
        // 관리자가 아닌 경우 본인 부서만 조회 가능
        if (!isAdmin()) {
            deptCode = userInfo.get("deptCode");
        }
        
        return attendanceService.getRealtimeStatus(comId, deptCode);
    }

    /**
     * IP 검증
     * @param requestIp - 요청 IP
     * @return 검증 결과
     */
    @GetMapping("/validateIp")
    public boolean validateIp(@RequestParam String requestIp) {
        Map<String, String> userInfo = getCurrentUserInfo();
        return attendanceService.validateIp(requestIp, userInfo.get("comId"), userInfo.get("empId"));
    }

    /**
     * GPS 검증
     * @param gpsInfo - GPS 정보
     * @return 검증 결과
     */
    @GetMapping("/validateGps")
    public boolean validateGps(@RequestParam String gpsInfo) {
        Map<String, String> userInfo = getCurrentUserInfo();
        return attendanceService.validateGps(gpsInfo, userInfo.get("comId"), userInfo.get("empId"));
    }

    /**
     * 지각/조퇴 통계 조회 (관리자만)
     * @param comId - 회사ID
     * @param fromDate - 시작일
     * @param toDate - 종료일
     * @return 통계 정보
     */
    @GetMapping("/attendanceStats")
    public Map<String, Object> getAttendanceStats(@RequestParam String comId, 
                                                  @RequestParam String fromDate, 
                                                  @RequestParam String toDate) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return null;
        }
        
        Map<String, String> userInfo = getCurrentUserInfo();
        String deptCode = userInfo.get("deptCode");
        
        // TODO: 통계 서비스 구현 필요
        return new java.util.HashMap<>();
    }

    /**
     * 테스트용 근태 데이터 생성 (개발용)
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 테스트 데이터
     */
    @GetMapping("/testAttendanceData")
    public List<AttendanceVO> getTestAttendanceData(@RequestParam String comId, @RequestParam String empId) {
        List<AttendanceVO> testData = new java.util.ArrayList<>();
        
        // 오늘부터 7일 전까지의 테스트 데이터 생성
        for (int i = 6; i >= 0; i--) {
            AttendanceVO vo = new AttendanceVO();
            vo.setAttendanceCode("ATT" + System.currentTimeMillis() + i);
            vo.setComId(comId);
            vo.setEmpId(empId);
            vo.setWorkDate(java.time.LocalDate.now().minusDays(i));
            
            // 출근시간 (9시 + 랜덤 지각)
            java.time.LocalDateTime startTime = java.time.LocalDateTime.of(
                vo.getWorkDate(), 
                java.time.LocalTime.of(9, 0)
            ).plusMinutes((long) (Math.random() * 30)); // 0-30분 지각
            
            // 퇴근시간 (18시 + 랜덤 연장)
            java.time.LocalDateTime endTime = java.time.LocalDateTime.of(
                vo.getWorkDate(), 
                java.time.LocalTime.of(18, 0)
            ).plusMinutes((long) (Math.random() * 120)); // 0-2시간 연장
            
            vo.setActualStartTime(startTime);
            vo.setActualEndTime(endTime);
            vo.setStandardStartTime(java.time.LocalDateTime.of(vo.getWorkDate(), java.time.LocalTime.of(9, 0)));
            vo.setStandardEndTime(java.time.LocalDateTime.of(vo.getWorkDate(), java.time.LocalTime.of(18, 0)));
            
            // 상태 판정
            if (startTime.isAfter(vo.getStandardStartTime())) {
                vo.setStatus("LATE");
            } else {
                vo.setStatus("NORMAL");
            }
            
            // 연장근무 시간 계산
            if (endTime.isAfter(vo.getStandardEndTime())) {
                long overtimeMinutes = java.time.Duration.between(vo.getStandardEndTime(), endTime).toMinutes();
                vo.setOvertimeHrs(java.math.BigDecimal.valueOf(overtimeMinutes / 60.0));
            } else {
                vo.setOvertimeHrs(java.math.BigDecimal.ZERO);
            }
            
            vo.setCheckIp("192.168.1.100");
            vo.setGpsInfo("37.5665,126.9780");
            vo.setLocationInfo("서울시 강남구");
            vo.setCreatedAt(java.time.LocalDateTime.now());
            
            testData.add(vo);
        }
        
        return testData;
    }
} 