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
     * 출근 등록 (보안 검증 포함) - 강화된 위치 기반 검증
     * @param vo - 근태 정보
     * @param request - 요청 객체
     * @return 처리 결과
     */
    @PostMapping("/attendanceCheckIn")
    public Map<String, Object> checkIn(@RequestBody AttendanceVO vo, HttpServletRequest request) {
        Map<String, Object> result = new java.util.HashMap<>();
        
        try {
            Map<String, String> userInfo = getCurrentUserInfo();
            
            // 본인만 출근 등록 가능
            if (!vo.getEmpId().equals(userInfo.get("empId"))) {
                result.put("success", false);
                result.put("message", "본인만 출근 등록이 가능합니다.");
                return result;
            }
            
            String ip = request.getRemoteAddr();
            String gpsInfo = request.getHeader("GPS-Info");
            String locationInfo = request.getHeader("Location-Info");
            
            // 강화된 위치 검증
            boolean locationValid = validateLocation(vo.getComId(), gpsInfo, locationInfo);
            if (!locationValid) {
                result.put("success", false);
                result.put("message", "회사 위치에서 출근해주세요. (GPS/위치 검증 실패)");
                return result;
            }
            
            // IP 검증
            boolean ipValid = attendanceService.validateIp(ip, vo.getComId(), vo.getEmpId());
            if (!ipValid) {
                result.put("success", false);
                result.put("message", "허용되지 않은 IP입니다.");
                return result;
            }
            
            String serviceResult = attendanceService.checkIn(vo, ip, gpsInfo, locationInfo);
            result.put("success", true);
            result.put("message", serviceResult);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "출근 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 퇴근 등록 (보안 검증 포함) - 강화된 위치 기반 검증
     * @param vo - 근태 정보
     * @param request - 요청 객체
     * @return 처리 결과
     */
    @PostMapping("/attendanceCheckOut")
    public Map<String, Object> checkOut(@RequestBody AttendanceVO vo, HttpServletRequest request) {
        Map<String, Object> result = new java.util.HashMap<>();
        
        try {
            Map<String, String> userInfo = getCurrentUserInfo();
            
            // 본인만 퇴근 등록 가능
            if (!vo.getEmpId().equals(userInfo.get("empId"))) {
                result.put("success", false);
                result.put("message", "본인만 퇴근 등록이 가능합니다.");
                return result;
            }
            
            String ip = request.getRemoteAddr();
            String gpsInfo = request.getHeader("GPS-Info");
            String locationInfo = request.getHeader("Location-Info");
            
            // 강화된 위치 검증
            boolean locationValid = validateLocation(vo.getComId(), gpsInfo, locationInfo);
            if (!locationValid) {
                result.put("success", false);
                result.put("message", "회사 위치에서 퇴근해주세요. (GPS/위치 검증 실패)");
                return result;
            }
            
            // IP 검증
            boolean ipValid = attendanceService.validateIp(ip, vo.getComId(), vo.getEmpId());
            if (!ipValid) {
                result.put("success", false);
                result.put("message", "허용되지 않은 IP입니다.");
                return result;
            }
            
            String serviceResult = attendanceService.checkOut(vo, ip, gpsInfo, locationInfo);
            result.put("success", true);
            result.put("message", serviceResult);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "퇴근 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 강화된 위치 검증 (GPS + IP 기반)
     * @param comId - 회사ID
     * @param gpsInfo - GPS 정보
     * @param locationInfo - 위치 정보
     * @return 검증 결과
     */
    private boolean validateLocation(String comId, String gpsInfo, String locationInfo) {
        // 1. GPS 정보 검증
        if (gpsInfo != null && !gpsInfo.isEmpty()) {
            boolean gpsValid = attendanceService.validateGps(gpsInfo, comId, "");
            if (!gpsValid) {
                return false;
            }
        }
        
        // 2. 위치 정보 검증 (추가 검증 로직)
        if (locationInfo != null && !locationInfo.isEmpty()) {
            // 위치 정보 파싱 및 검증
            String[] locationParts = locationInfo.split(",");
            if (locationParts.length >= 2) {
                try {
                    double lat = Double.parseDouble(locationParts[0]);
                    double lng = Double.parseDouble(locationParts[1]);
                    
                    // 회사 위치와의 거리 계산 (100m 이내)
                    double companyLat = 37.5665; // 예시: 서울시청 위도
                    double companyLng = 126.9780; // 예시: 서울시청 경도
                    
                    double distance = calculateDistance(lat, lng, companyLat, companyLng);
                    if (distance > 100) { // 100m 초과 시 거부
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * 두 지점 간의 거리 계산 (미터 단위)
     * @param lat1 - 첫 번째 지점 위도
     * @param lng1 - 첫 번째 지점 경도
     * @param lat2 - 두 번째 지점 위도
     * @param lng2 - 두 번째 지점 경도
     * @return 거리 (미터)
     */
    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371000; // 지구 반지름 (미터)
        
        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);
        
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c;
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
     * 위치 검증 (새로운 API)
     * @param gpsInfo - GPS 정보
     * @param locationInfo - 위치 정보
     * @return 검증 결과
     */
    @GetMapping("/validateLocation")
    public Map<String, Object> validateLocation(@RequestParam(required = false) String gpsInfo,
                                               @RequestParam(required = false) String locationInfo) {
        Map<String, Object> result = new java.util.HashMap<>();
        Map<String, String> userInfo = getCurrentUserInfo();
        
        boolean isValid = validateLocation(userInfo.get("comId"), gpsInfo, locationInfo);
        result.put("valid", isValid);
        result.put("message", isValid ? "위치 검증 성공" : "위치 검증 실패");
        
        return result;
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
    public Map<String, Object> getTestAttendanceData(@RequestParam String comId, @RequestParam String empId) {
        Map<String, Object> result = new java.util.HashMap<>();
        result.put("comId", comId);
        result.put("empId", empId);
        result.put("testData", "테스트 근태 데이터");
        return result;
    }
} 