package com.dadam.hr.employee.service;

import com.dadam.hr.employee.mapper.EmployeeHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * 사원 이력관리 서비스 (교수님 요구사항 반영)
 * - 입사/퇴사/부서이동 이력 관리
 * - 이력 조회 및 통계
 * - 이력 변경 알림
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2025-07-11
 */
@Slf4j
@Service
@Transactional
public class EmployeeHistoryService {

    @Autowired
    private EmployeeHistoryMapper employeeHistoryMapper;

    /**
     * 사원 이력 생성
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param changeType 변경 유형 (HIRE, RESIGN, TRANSFER, PROMOTION)
     * @param changeDetails 변경 상세 정보
     * @return 생성된 이력 ID
     */
    public Long createEmployeeHistory(String empId, String comId, String changeType, Map<String, Object> changeDetails) {
        try {
            Map<String, Object> history = new HashMap<>();
            history.put("empId", empId);
            history.put("comId", comId);
            history.put("changeType", changeType);
            history.put("changeDetails", changeDetails);
            history.put("changeDate", LocalDateTime.now());
            history.put("createdAt", LocalDateTime.now());
            
            // 이력 생성
            employeeHistoryMapper.insertEmployeeHistory(history);
            
            Long historyId = (Long) history.get("historyId");
            
            // 이력 변경 알림 생성
            createHistoryChangeNotification(empId, comId, changeType, changeDetails);
            
            log.info("사원 이력 생성 완료 - 사원ID: {}, 변경유형: {}, 이력ID: {}", empId, changeType, historyId);
            
            return historyId;
            
        } catch (Exception e) {
            log.error("사원 이력 생성 중 오류 발생", e);
            throw new RuntimeException("사원 이력 생성 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 입사 이력 생성
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param hireInfo 입사 정보
     * @return 이력 ID
     */
    public Long createHireHistory(String empId, String comId, Map<String, Object> hireInfo) {
        Map<String, Object> changeDetails = new HashMap<>();
        changeDetails.put("hireDate", hireInfo.get("hireDate"));
        changeDetails.put("position", hireInfo.get("position"));
        changeDetails.put("department", hireInfo.get("department"));
        changeDetails.put("salary", hireInfo.get("salary"));
        changeDetails.put("empType", hireInfo.get("empType"));
        
        return createEmployeeHistory(empId, comId, "HIRE", changeDetails);
    }

    /**
     * 퇴사 이력 생성
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param resignInfo 퇴사 정보
     * @return 이력 ID
     */
    public Long createResignHistory(String empId, String comId, Map<String, Object> resignInfo) {
        Map<String, Object> changeDetails = new HashMap<>();
        changeDetails.put("resignDate", resignInfo.get("resignDate"));
        changeDetails.put("resignReason", resignInfo.get("resignReason"));
        changeDetails.put("lastWorkDate", resignInfo.get("lastWorkDate"));
        changeDetails.put("severancePay", resignInfo.get("severancePay"));
        
        return createEmployeeHistory(empId, comId, "RESIGN", changeDetails);
    }

    /**
     * 부서이동 이력 생성
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param transferInfo 부서이동 정보
     * @return 이력 ID
     */
    public Long createTransferHistory(String empId, String comId, Map<String, Object> transferInfo) {
        Map<String, Object> changeDetails = new HashMap<>();
        changeDetails.put("fromDepartment", transferInfo.get("fromDepartment"));
        changeDetails.put("toDepartment", transferInfo.get("toDepartment"));
        changeDetails.put("fromPosition", transferInfo.get("fromPosition"));
        changeDetails.put("toPosition", transferInfo.get("toPosition"));
        changeDetails.put("transferDate", transferInfo.get("transferDate"));
        changeDetails.put("transferReason", transferInfo.get("transferReason"));
        
        return createEmployeeHistory(empId, comId, "TRANSFER", changeDetails);
    }

    /**
     * 승진 이력 생성
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param promotionInfo 승진 정보
     * @return 이력 ID
     */
    public Long createPromotionHistory(String empId, String comId, Map<String, Object> promotionInfo) {
        Map<String, Object> changeDetails = new HashMap<>();
        changeDetails.put("fromPosition", promotionInfo.get("fromPosition"));
        changeDetails.put("toPosition", promotionInfo.get("toPosition"));
        changeDetails.put("promotionDate", promotionInfo.get("promotionDate"));
        changeDetails.put("salaryIncrease", promotionInfo.get("salaryIncrease"));
        changeDetails.put("promotionReason", promotionInfo.get("promotionReason"));
        
        return createEmployeeHistory(empId, comId, "PROMOTION", changeDetails);
    }

    /**
     * 사원 이력 조회
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @return 이력 목록
     */
    public List<Map<String, Object>> getEmployeeHistory(String empId, String comId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("empId", empId);
            params.put("comId", comId);
            
            List<Map<String, Object>> history = employeeHistoryMapper.selectEmployeeHistory(params);
            
            log.info("사원 이력 조회 완료 - 사원ID: {}, 이력건수: {}", empId, history.size());
            
            return history;
            
        } catch (Exception e) {
            log.error("사원 이력 조회 중 오류 발생", e);
            throw new RuntimeException("사원 이력 조회 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 회사별 이력 통계 조회
     * 
     * @param comId 회사 ID
     * @param fromDate 시작일
     * @param toDate 종료일
     * @return 이력 통계
     */
    public Map<String, Object> getEmployeeHistoryStats(String comId, String fromDate, String toDate) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("comId", comId);
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
            
            // 1. 변경 유형별 통계
            List<Map<String, Object>> changeTypeStats = employeeHistoryMapper.getChangeTypeStats(params);
            
            // 2. 부서별 이력 통계
            List<Map<String, Object>> deptHistoryStats = employeeHistoryMapper.getDeptHistoryStats(params);
            
            // 3. 월별 이력 통계
            List<Map<String, Object>> monthlyHistoryStats = employeeHistoryMapper.getMonthlyHistoryStats(params);
            
            Map<String, Object> result = new HashMap<>();
            result.put("changeTypeStats", changeTypeStats);
            result.put("deptHistoryStats", deptHistoryStats);
            result.put("monthlyHistoryStats", monthlyHistoryStats);
            
            log.info("회사 이력 통계 조회 완료 - 회사ID: {}", comId);
            
            return result;
            
        } catch (Exception e) {
            log.error("회사 이력 통계 조회 중 오류 발생", e);
            throw new RuntimeException("회사 이력 통계 조회 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 이력 변경 알림 생성
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param changeType 변경 유형
     * @param changeDetails 변경 상세 정보
     */
    private void createHistoryChangeNotification(String empId, String comId, String changeType, Map<String, Object> changeDetails) {
        try {
            Map<String, Object> notification = new HashMap<>();
            notification.put("empId", empId);
            notification.put("comId", comId);
            notification.put("notificationType", "HISTORY_CHANGE");
            notification.put("changeType", changeType);
            notification.put("changeDetails", changeDetails);
            notification.put("notificationDate", LocalDateTime.now());
            notification.put("status", "PENDING");
            
            employeeHistoryMapper.insertHistoryNotification(notification);
            
            log.info("이력 변경 알림 생성 - 사원ID: {}, 변경유형: {}", empId, changeType);
            
        } catch (Exception e) {
            log.error("이력 변경 알림 생성 중 오류 발생", e);
        }
    }

    /**
     * 사원 이력 엑셀 내보내기
     * 
     * @param comId 회사 ID
     * @param fromDate 시작일
     * @param toDate 종료일
     * @return 엑셀 데이터
     */
    public byte[] exportEmployeeHistoryExcel(String comId, String fromDate, String toDate) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("comId", comId);
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
            
            // 이력 데이터 조회
            List<Map<String, Object>> historyData = employeeHistoryMapper.getEmployeeHistoryForExport(params);
            
            // 엑셀 파일 생성
            byte[] excelData = createEmployeeHistoryExcel(historyData, fromDate, toDate);
            
            log.info("사원 이력 엑셀 내보내기 완료 - 회사ID: {}, 데이터건수: {}", comId, historyData.size());
            
            return excelData;
            
        } catch (Exception e) {
            log.error("사원 이력 엑셀 내보내기 중 오류 발생", e);
            throw new RuntimeException("사원 이력 엑셀 내보내기 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 사원 이력 엑셀 파일 생성
     * 
     * @param historyData 이력 데이터
     * @param fromDate 시작일
     * @param toDate 종료일
     * @return 엑셀 데이터
     */
    private byte[] createEmployeeHistoryExcel(List<Map<String, Object>> historyData, String fromDate, String toDate) {
        // 실제 구현 시에는 Apache POI나 EasyExcel 등을 사용하여 엑셀 생성
        // 여기서는 모의 엑셀 데이터 반환
        
        StringBuilder excelContent = new StringBuilder();
        excelContent.append("사원 이력 보고서\n");
        excelContent.append("조회기간: ").append(fromDate).append(" ~ ").append(toDate).append("\n");
        excelContent.append("생성일시: ").append(LocalDateTime.now()).append("\n\n");
        
        excelContent.append("사원ID,변경유형,변경일시,변경상세\n");
        
        for (Map<String, Object> history : historyData) {
            excelContent.append(history.get("empId")).append(",");
            excelContent.append(history.get("changeType")).append(",");
            excelContent.append(history.get("changeDate")).append(",");
            excelContent.append(history.get("changeDetails")).append("\n");
        }
        
        return excelContent.toString().getBytes();
    }

    /**
     * 사원 이력 삭제 (관리자 전용)
     * 
     * @param historyId 이력 ID
     * @param comId 회사 ID
     * @return 삭제 결과
     */
    public boolean deleteEmployeeHistory(Long historyId, String comId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("historyId", historyId);
            params.put("comId", comId);
            
            int result = employeeHistoryMapper.deleteEmployeeHistory(params);
            
            if (result > 0) {
                log.info("사원 이력 삭제 완료 - 이력ID: {}", historyId);
                return true;
            } else {
                log.warn("사원 이력 삭제 실패 - 이력ID: {}", historyId);
                return false;
            }
            
        } catch (Exception e) {
            log.error("사원 이력 삭제 중 오류 발생", e);
            return false;
        }
    }

    /**
     * 사원 이력 수정 (관리자 전용)
     * 
     * @param historyId 이력 ID
     * @param comId 회사 ID
     * @param changeDetails 수정할 변경 상세 정보
     * @return 수정 결과
     */
    public boolean updateEmployeeHistory(Long historyId, String comId, Map<String, Object> changeDetails) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("historyId", historyId);
            params.put("comId", comId);
            params.put("changeDetails", changeDetails);
            params.put("updatedAt", LocalDateTime.now());
            
            int result = employeeHistoryMapper.updateEmployeeHistory(params);
            
            if (result > 0) {
                log.info("사원 이력 수정 완료 - 이력ID: {}", historyId);
                return true;
            } else {
                log.warn("사원 이력 수정 실패 - 이력ID: {}", historyId);
                return false;
            }
            
        } catch (Exception e) {
            log.error("사원 이력 수정 중 오류 발생", e);
            return false;
        }
    }
} 