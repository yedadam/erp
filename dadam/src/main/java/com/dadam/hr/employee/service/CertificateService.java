package com.dadam.hr.employee.service;

import com.dadam.hr.employee.mapper.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * 인사증명서 발급 서비스 (교수님 요구사항 반영)
 * - 각종 인사증명서 인쇄
 * - 증명서 발급 이력 관리
 * - 증명서 템플릿 관리
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2025-07-11
 */
@Slf4j
@Service
public class CertificateService {

    @Autowired
    private CertificateMapper certificateMapper;

    /**
     * 재직증명서 발급
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param requestInfo 발급 요청 정보
     * @return 증명서 데이터
     */
    public Map<String, Object> issueEmploymentCertificate(String empId, String comId, Map<String, Object> requestInfo) {
        try {
            // 1. 사원 정보 조회
            Map<String, Object> employeeInfo = certificateMapper.getEmployeeInfo(empId, comId);
            
            if (employeeInfo == null) {
                throw new RuntimeException("사원 정보를 찾을 수 없습니다.");
            }
            
            // 2. 재직증명서 데이터 생성
            Map<String, Object> certificateData = createEmploymentCertificateData(employeeInfo, requestInfo);
            
            // 3. 증명서 발급 이력 저장
            saveCertificateHistory(empId, comId, "EMPLOYMENT", requestInfo);
            
            log.info("재직증명서 발급 완료 - 사원ID: {}", empId);
            
            return certificateData;
            
        } catch (Exception e) {
            log.error("재직증명서 발급 중 오류 발생", e);
            throw new RuntimeException("재직증명서 발급 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 경력증명서 발급
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param requestInfo 발급 요청 정보
     * @return 증명서 데이터
     */
    public Map<String, Object> issueCareerCertificate(String empId, String comId, Map<String, Object> requestInfo) {
        try {
            // 1. 사원 정보 및 경력 조회
            Map<String, Object> employeeInfo = certificateMapper.getEmployeeInfo(empId, comId);
            List<Map<String, Object>> careerHistory = certificateMapper.getCareerHistory(empId, comId);
            
            if (employeeInfo == null) {
                throw new RuntimeException("사원 정보를 찾을 수 없습니다.");
            }
            
            // 2. 경력증명서 데이터 생성
            Map<String, Object> certificateData = createCareerCertificateData(employeeInfo, careerHistory, requestInfo);
            
            // 3. 증명서 발급 이력 저장
            saveCertificateHistory(empId, comId, "CAREER", requestInfo);
            
            log.info("경력증명서 발급 완료 - 사원ID: {}", empId);
            
            return certificateData;
            
        } catch (Exception e) {
            log.error("경력증명서 발급 중 오류 발생", e);
            throw new RuntimeException("경력증명서 발급 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 급여증명서 발급
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param requestInfo 발급 요청 정보
     * @return 증명서 데이터
     */
    public Map<String, Object> issueSalaryCertificate(String empId, String comId, Map<String, Object> requestInfo) {
        try {
            // 1. 사원 정보 및 급여 정보 조회
            Map<String, Object> employeeInfo = certificateMapper.getEmployeeInfo(empId, comId);
            List<Map<String, Object>> salaryHistory = certificateMapper.getSalaryHistory(empId, comId);
            
            if (employeeInfo == null) {
                throw new RuntimeException("사원 정보를 찾을 수 없습니다.");
            }
            
            // 2. 급여증명서 데이터 생성
            Map<String, Object> certificateData = createSalaryCertificateData(employeeInfo, salaryHistory, requestInfo);
            
            // 3. 증명서 발급 이력 저장
            saveCertificateHistory(empId, comId, "SALARY", requestInfo);
            
            log.info("급여증명서 발급 완료 - 사원ID: {}", empId);
            
            return certificateData;
            
        } catch (Exception e) {
            log.error("급여증명서 발급 중 오류 발생", e);
            throw new RuntimeException("급여증명서 발급 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 퇴직증명서 발급
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param requestInfo 발급 요청 정보
     * @return 증명서 데이터
     */
    public Map<String, Object> issueResignationCertificate(String empId, String comId, Map<String, Object> requestInfo) {
        try {
            // 1. 사원 정보 및 퇴직 정보 조회
            Map<String, Object> employeeInfo = certificateMapper.getEmployeeInfo(empId, comId);
            Map<String, Object> resignationInfo = certificateMapper.getResignationInfo(empId, comId);
            
            if (employeeInfo == null) {
                throw new RuntimeException("사원 정보를 찾을 수 없습니다.");
            }
            
            // 2. 퇴직증명서 데이터 생성
            Map<String, Object> certificateData = createResignationCertificateData(employeeInfo, resignationInfo, requestInfo);
            
            // 3. 증명서 발급 이력 저장
            saveCertificateHistory(empId, comId, "RESIGNATION", requestInfo);
            
            log.info("퇴직증명서 발급 완료 - 사원ID: {}", empId);
            
            return certificateData;
            
        } catch (Exception e) {
            log.error("퇴직증명서 발급 중 오류 발생", e);
            throw new RuntimeException("퇴직증명서 발급 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 재직증명서 데이터 생성
     * 
     * @param employeeInfo 사원 정보
     * @param requestInfo 발급 요청 정보
     * @return 증명서 데이터
     */
    private Map<String, Object> createEmploymentCertificateData(Map<String, Object> employeeInfo, Map<String, Object> requestInfo) {
        Map<String, Object> certificateData = new HashMap<>();
        
        // 기본 정보
        certificateData.put("certificateType", "EMPLOYMENT");
        certificateData.put("issueDate", LocalDateTime.now());
        certificateData.put("requestPurpose", requestInfo.get("purpose"));
        certificateData.put("requestCount", requestInfo.get("count"));
        
        // 사원 정보
        certificateData.put("empId", employeeInfo.get("empId"));
        certificateData.put("empName", employeeInfo.get("empName"));
        certificateData.put("department", employeeInfo.get("department"));
        certificateData.put("position", employeeInfo.get("position"));
        certificateData.put("hireDate", employeeInfo.get("hireDate"));
        certificateData.put("empType", employeeInfo.get("empType"));
        certificateData.put("salary", employeeInfo.get("salary"));
        
        // 회사 정보
        certificateData.put("companyName", employeeInfo.get("companyName"));
        certificateData.put("companyAddress", employeeInfo.get("companyAddress"));
        certificateData.put("companyPhone", employeeInfo.get("companyPhone"));
        
        // 증명서 번호 생성
        String certificateNumber = generateCertificateNumber("EMPLOYMENT", employeeInfo.get("empId").toString());
        certificateData.put("certificateNumber", certificateNumber);
        
        return certificateData;
    }

    /**
     * 경력증명서 데이터 생성
     * 
     * @param employeeInfo 사원 정보
     * @param careerHistory 경력 이력
     * @param requestInfo 발급 요청 정보
     * @return 증명서 데이터
     */
    private Map<String, Object> createCareerCertificateData(Map<String, Object> employeeInfo, 
                                                           List<Map<String, Object>> careerHistory,
                                                           Map<String, Object> requestInfo) {
        Map<String, Object> certificateData = new HashMap<>();
        
        // 기본 정보
        certificateData.put("certificateType", "CAREER");
        certificateData.put("issueDate", LocalDateTime.now());
        certificateData.put("requestPurpose", requestInfo.get("purpose"));
        certificateData.put("requestCount", requestInfo.get("count"));
        
        // 사원 정보
        certificateData.put("empId", employeeInfo.get("empId"));
        certificateData.put("empName", employeeInfo.get("empName"));
        certificateData.put("department", employeeInfo.get("department"));
        certificateData.put("position", employeeInfo.get("position"));
        certificateData.put("hireDate", employeeInfo.get("hireDate"));
        
        // 경력 정보
        certificateData.put("careerHistory", careerHistory);
        
        // 회사 정보
        certificateData.put("companyName", employeeInfo.get("companyName"));
        certificateData.put("companyAddress", employeeInfo.get("companyAddress"));
        
        // 증명서 번호 생성
        String certificateNumber = generateCertificateNumber("CAREER", employeeInfo.get("empId").toString());
        certificateData.put("certificateNumber", certificateNumber);
        
        return certificateData;
    }

    /**
     * 급여증명서 데이터 생성
     * 
     * @param employeeInfo 사원 정보
     * @param salaryHistory 급여 이력
     * @param requestInfo 발급 요청 정보
     * @return 증명서 데이터
     */
    private Map<String, Object> createSalaryCertificateData(Map<String, Object> employeeInfo,
                                                           List<Map<String, Object>> salaryHistory,
                                                           Map<String, Object> requestInfo) {
        Map<String, Object> certificateData = new HashMap<>();
        
        // 기본 정보
        certificateData.put("certificateType", "SALARY");
        certificateData.put("issueDate", LocalDateTime.now());
        certificateData.put("requestPurpose", requestInfo.get("purpose"));
        certificateData.put("requestCount", requestInfo.get("count"));
        
        // 사원 정보
        certificateData.put("empId", employeeInfo.get("empId"));
        certificateData.put("empName", employeeInfo.get("empName"));
        certificateData.put("department", employeeInfo.get("department"));
        certificateData.put("position", employeeInfo.get("position"));
        
        // 급여 정보
        certificateData.put("salaryHistory", salaryHistory);
        
        // 회사 정보
        certificateData.put("companyName", employeeInfo.get("companyName"));
        certificateData.put("companyAddress", employeeInfo.get("companyAddress"));
        
        // 증명서 번호 생성
        String certificateNumber = generateCertificateNumber("SALARY", employeeInfo.get("empId").toString());
        certificateData.put("certificateNumber", certificateNumber);
        
        return certificateData;
    }

    /**
     * 퇴직증명서 데이터 생성
     * 
     * @param employeeInfo 사원 정보
     * @param resignationInfo 퇴직 정보
     * @param requestInfo 발급 요청 정보
     * @return 증명서 데이터
     */
    private Map<String, Object> createResignationCertificateData(Map<String, Object> employeeInfo,
                                                                Map<String, Object> resignationInfo,
                                                                Map<String, Object> requestInfo) {
        Map<String, Object> certificateData = new HashMap<>();
        
        // 기본 정보
        certificateData.put("certificateType", "RESIGNATION");
        certificateData.put("issueDate", LocalDateTime.now());
        certificateData.put("requestPurpose", requestInfo.get("purpose"));
        certificateData.put("requestCount", requestInfo.get("count"));
        
        // 사원 정보
        certificateData.put("empId", employeeInfo.get("empId"));
        certificateData.put("empName", employeeInfo.get("empName"));
        certificateData.put("department", employeeInfo.get("department"));
        certificateData.put("position", employeeInfo.get("position"));
        certificateData.put("hireDate", employeeInfo.get("hireDate"));
        
        // 퇴직 정보
        certificateData.put("resignationDate", resignationInfo.get("resignationDate"));
        certificateData.put("lastWorkDate", resignationInfo.get("lastWorkDate"));
        certificateData.put("resignationReason", resignationInfo.get("resignationReason"));
        certificateData.put("severancePay", resignationInfo.get("severancePay"));
        
        // 회사 정보
        certificateData.put("companyName", employeeInfo.get("companyName"));
        certificateData.put("companyAddress", employeeInfo.get("companyAddress"));
        
        // 증명서 번호 생성
        String certificateNumber = generateCertificateNumber("RESIGNATION", employeeInfo.get("empId").toString());
        certificateData.put("certificateNumber", certificateNumber);
        
        return certificateData;
    }

    /**
     * 증명서 번호 생성
     * 
     * @param certificateType 증명서 유형
     * @param empId 사원 ID
     * @return 증명서 번호
     */
    private String generateCertificateNumber(String certificateType, String empId) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return certificateType + "_" + empId + "_" + timestamp;
    }

    /**
     * 증명서 발급 이력 저장
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param certificateType 증명서 유형
     * @param requestInfo 발급 요청 정보
     */
    private void saveCertificateHistory(String empId, String comId, String certificateType, Map<String, Object> requestInfo) {
        try {
            Map<String, Object> history = new HashMap<>();
            history.put("empId", empId);
            history.put("comId", comId);
            history.put("certificateType", certificateType);
            history.put("requestPurpose", requestInfo.get("purpose"));
            history.put("requestCount", requestInfo.get("count"));
            history.put("requestDate", LocalDateTime.now());
            history.put("status", "ISSUED");
            
            certificateMapper.insertCertificateHistory(history);
            
        } catch (Exception e) {
            log.error("증명서 발급 이력 저장 중 오류 발생", e);
        }
    }

    /**
     * 증명서 발급 이력 조회
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @return 발급 이력
     */
    public List<Map<String, Object>> getCertificateHistory(String empId, String comId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("empId", empId);
            params.put("comId", comId);
            
            List<Map<String, Object>> history = certificateMapper.selectCertificateHistory(params);
            
            log.info("증명서 발급 이력 조회 완료 - 사원ID: {}, 이력건수: {}", empId, history.size());
            
            return history;
            
        } catch (Exception e) {
            log.error("증명서 발급 이력 조회 중 오류 발생", e);
            throw new RuntimeException("증명서 발급 이력 조회 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 증명서 템플릿 조회
     * 
     * @param certificateType 증명서 유형
     * @param comId 회사 ID
     * @return 템플릿 정보
     */
    public Map<String, Object> getCertificateTemplate(String certificateType, String comId) {
        try {
            Map<String, Object> template = certificateMapper.getCertificateTemplate(certificateType, comId);
            
            if (template == null) {
                // 기본 템플릿 반환
                template = getDefaultTemplate(certificateType);
            }
            
            return template;
            
        } catch (Exception e) {
            log.error("증명서 템플릿 조회 중 오류 발생", e);
            return getDefaultTemplate(certificateType);
        }
    }

    /**
     * 기본 증명서 템플릿 반환
     * 
     * @param certificateType 증명서 유형
     * @return 기본 템플릿
     */
    private Map<String, Object> getDefaultTemplate(String certificateType) {
        Map<String, Object> template = new HashMap<>();
        
        switch (certificateType) {
            case "EMPLOYMENT":
                template.put("title", "재직증명서");
                template.put("content", "위와 같이 재직 중임을 증명합니다.");
                break;
            case "CAREER":
                template.put("title", "경력증명서");
                template.put("content", "위와 같이 경력이 있음을 증명합니다.");
                break;
            case "SALARY":
                template.put("title", "급여증명서");
                template.put("content", "위와 같이 급여를 지급받고 있음을 증명합니다.");
                break;
            case "RESIGNATION":
                template.put("title", "퇴직증명서");
                template.put("content", "위와 같이 퇴직하였음을 증명합니다.");
                break;
            default:
                template.put("title", "증명서");
                template.put("content", "위와 같이 증명합니다.");
        }
        
        return template;
    }
} 