package com.dadam.hr.salary.web;

import com.dadam.hr.salary.service.SalaryCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * 급여대장 엑셀 전송 컨트롤러 (교수님 요구사항 반영)
 * - 급여대장 엑셀 생성 및 회계팀 전송
 * - A/B/C 타입별 급여 항목 포함
 * - PDF/엑셀 다운로드 지원
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2025-07-11
 */
@Slf4j
@RestController
@RequestMapping("/erp/hr/salary")
public class SalaryExportController {

    @Autowired
    private SalaryCalculationService salaryCalculationService;

    /**
     * 급여대장 엑셀 생성 및 다운로드
     * 
     * @param comId 회사 ID
     * @param yearMonth 급여 년월 (YYYY-MM)
     * @param status 급여 상태 (CALCULATED, APPROVED, PAID)
     * @return 엑셀 파일
     */
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportSalaryExcel(
            @RequestParam String comId,
            @RequestParam String yearMonth,
            @RequestParam(required = false, defaultValue = "APPROVED") String status) {
        
        log.info("급여대장 엑셀 생성 - 회사ID: {}, 년월: {}, 상태: {}", comId, yearMonth, status);
        
        try {
            // 급여대장 엑셀 생성
            byte[] excelData = salaryCalculationService.exportSalaryExcel(comId, yearMonth, status);
            
            // 파일명 생성
            String fileName = String.format("급여대장_%s_%s.xlsx", comId, yearMonth);
            
            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(excelData.length);
            
            log.info("급여대장 엑셀 생성 완료 - 파일명: {}", fileName);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelData);
                    
        } catch (Exception e) {
            log.error("급여대장 엑셀 생성 중 오류 발생", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 급여대장 회계팀 전송
     * 
     * @param request 전송 요청 정보
     * @return 전송 결과
     */
    @PostMapping("/export/send-to-accounting")
    public ResponseEntity<Map<String, Object>> sendToAccounting(@RequestBody Map<String, Object> request) {
        log.info("급여대장 회계팀 전송 - 회사ID: {}, 년월: {}", 
                request.get("comId"), request.get("yearMonth"));
        
        try {
            String comId = (String) request.get("comId");
            String yearMonth = (String) request.get("yearMonth");
            String status = (String) request.getOrDefault("status", "APPROVED");
            
            // 1. 급여대장 엑셀 생성
            byte[] excelData = salaryCalculationService.exportSalaryExcel(comId, yearMonth, status);
            
            // 2. 회계팀 이메일 전송 (실제 구현 시 이메일 서비스 연동)
            boolean emailSent = sendSalaryExcelToAccounting(comId, yearMonth, excelData);
            
            Map<String, Object> response = new HashMap<>();
            
            if (emailSent) {
                response.put("success", true);
                response.put("message", "급여대장이 회계팀에 성공적으로 전송되었습니다.");
                response.put("fileName", String.format("급여대장_%s_%s.xlsx", comId, yearMonth));
                
                log.info("급여대장 회계팀 전송 완료");
            } else {
                response.put("success", false);
                response.put("message", "회계팀 전송 중 오류가 발생했습니다.");
                
                log.error("급여대장 회계팀 전송 실패");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("급여대장 회계팀 전송 중 오류 발생", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "전송 중 오류가 발생했습니다: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 급여대장 회계팀 이메일 전송 (모의 구현)
     * 
     * @param comId 회사 ID
     * @param yearMonth 급여 년월
     * @param excelData 엑셀 데이터
     * @return 전송 성공 여부
     */
    private boolean sendSalaryExcelToAccounting(String comId, String yearMonth, byte[] excelData) {
        try {
            // 실제 구현 시에는 이메일 서비스를 통해 전송
            // 예: emailService.sendSalaryExcel(comId, yearMonth, excelData);
            
            log.info("급여대장 이메일 전송 - 회사ID: {}, 년월: {}, 파일크기: {} bytes", 
                    comId, yearMonth, excelData.length);
            
            // 모의 전송 성공
            return true;
            
        } catch (Exception e) {
            log.error("급여대장 이메일 전송 중 오류 발생", e);
            return false;
        }
    }

    /**
     * 급여대장 전송 이력 조회
     * 
     * @param comId 회사 ID
     * @param yearMonth 급여 년월
     * @return 전송 이력
     */
    @GetMapping("/export/history")
    public ResponseEntity<List<Map<String, Object>>> getExportHistory(
            @RequestParam String comId,
            @RequestParam String yearMonth) {
        
        log.info("급여대장 전송 이력 조회 - 회사ID: {}, 년월: {}", comId, yearMonth);
        
        try {
            // 전송 이력 조회 (실제 구현 시 DB에서 조회)
            List<Map<String, Object>> history = getSalaryExportHistory(comId, yearMonth);
            
            return ResponseEntity.ok(history);
            
        } catch (Exception e) {
            log.error("급여대장 전송 이력 조회 중 오류 발생", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 급여대장 전송 이력 조회 (모의 구현)
     * 
     * @param comId 회사 ID
     * @param yearMonth 급여 년월
     * @return 전송 이력
     */
    private List<Map<String, Object>> getSalaryExportHistory(String comId, String yearMonth) {
        List<Map<String, Object>> history = new java.util.ArrayList<>();
        
        // 모의 데이터
        Map<String, Object> record1 = new HashMap<>();
        record1.put("exportDate", "2025-07-11 14:30:00");
        record1.put("fileName", String.format("급여대장_%s_%s.xlsx", comId, yearMonth));
        record1.put("recipient", "accounting@company.com");
        record1.put("status", "SENT");
        record1.put("fileSize", "1.2MB");
        history.add(record1);
        
        Map<String, Object> record2 = new HashMap<>();
        record2.put("exportDate", "2025-07-10 09:15:00");
        record2.put("fileName", String.format("급여대장_%s_%s.xlsx", comId, yearMonth));
        record2.put("recipient", "hr@company.com");
        record2.put("status", "SENT");
        record2.put("fileSize", "1.1MB");
        history.add(record2);
        
        return history;
    }

    /**
     * 급여대장 PDF 생성 및 다운로드
     * 
     * @param comId 회사 ID
     * @param yearMonth 급여 년월
     * @param empId 사원 ID (개별 사원용)
     * @return PDF 파일
     */
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportSalaryPDF(
            @RequestParam String comId,
            @RequestParam String yearMonth,
            @RequestParam(required = false) String empId) {
        
        log.info("급여대장 PDF 생성 - 회사ID: {}, 년월: {}, 사원ID: {}", comId, yearMonth, empId);
        
        try {
            // PDF 생성 (실제 구현 시 PDF 라이브러리 사용)
            byte[] pdfData = generateSalaryPDF(comId, yearMonth, empId);
            
            // 파일명 생성
            String fileName = empId != null ? 
                String.format("급여명세서_%s_%s_%s.pdf", comId, yearMonth, empId) :
                String.format("급여대장_%s_%s.pdf", comId, yearMonth);
            
            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(pdfData.length);
            
            log.info("급여대장 PDF 생성 완료 - 파일명: {}", fileName);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfData);
                    
        } catch (Exception e) {
            log.error("급여대장 PDF 생성 중 오류 발생", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 급여대장 PDF 생성 (모의 구현)
     * 
     * @param comId 회사 ID
     * @param yearMonth 급여 년월
     * @param empId 사원 ID
     * @return PDF 데이터
     */
    private byte[] generateSalaryPDF(String comId, String yearMonth, String empId) {
        // 실제 구현 시에는 JasperReports나 iText 등을 사용하여 PDF 생성
        // 여기서는 모의 PDF 데이터 반환
        
        String pdfContent = String.format(
            "급여대장\n회사ID: %s\n년월: %s\n사원ID: %s\n생성일시: %s",
            comId, yearMonth, empId != null ? empId : "전체", 
            java.time.LocalDateTime.now().toString()
        );
        
        return pdfContent.getBytes();
    }

    /**
     * 급여대장 통계 조회
     * 
     * @param comId 회사 ID
     * @param yearMonth 급여 년월
     * @return 통계 정보
     */
    @GetMapping("/export/statistics")
    public ResponseEntity<Map<String, Object>> getExportStatistics(
            @RequestParam String comId,
            @RequestParam String yearMonth) {
        
        log.info("급여대장 통계 조회 - 회사ID: {}, 년월: {}", comId, yearMonth);
        
        try {
            Map<String, Object> statistics = new HashMap<>();
            
            // 급여 통계 정보
            statistics.put("totalEmployees", 150);
            statistics.put("totalSalaryAmount", 850000000);
            statistics.put("averageSalary", 5666667);
            statistics.put("calculatedCount", 145);
            statistics.put("approvedCount", 140);
            statistics.put("paidCount", 135);
            
            // A/B/C 타입별 통계
            Map<String, Object> typeStatistics = new HashMap<>();
            typeStatistics.put("typeA", 15000000); // 기본 수당
            typeStatistics.put("typeB", 25000000); // 성과 수당
            typeStatistics.put("typeC", 10000000); // 특별 수당
            statistics.put("allowanceByType", typeStatistics);
            
            // 4대보험 통계
            Map<String, Object> insuranceStatistics = new HashMap<>();
            insuranceStatistics.put("nationalPension", 76500000);
            insuranceStatistics.put("healthInsurance", 30142500);
            insuranceStatistics.put("employmentInsurance", 6800000);
            insuranceStatistics.put("industrialAccident", 6800000);
            statistics.put("insuranceByType", insuranceStatistics);
            
            return ResponseEntity.ok(statistics);
            
        } catch (Exception e) {
            log.error("급여대장 통계 조회 중 오류 발생", e);
            return ResponseEntity.badRequest().build();
        }
    }
} 