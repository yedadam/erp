package com.dadam.hr.salary.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 급여명세 리스트 조회 VO
 * 
 * @author 팀장
 * @since 2024-01-01
 */
public class SalaryListVO {
    
    // 검색 조건
    private String salaryYear;        // 급여년도
    private String salaryMonth;       // 급여월
    private String empName;           // 사원명
    private String status;            // 상태
    
    // 급여명세 정보
    private String salaryId;          // 급여명세 ID
    private String empNo;             // 사원번호
    private String deptName;          // 부서명
    private String positionName;      // 직급명
    private BigDecimal baseSalary;    // 기본급
    private BigDecimal netSalary;     // 실수령액
    private String createDate;        // 작성일시
    private String createUser;        // 작성자
    private String approveDate;       // 승인일시
    private String approveUser;       // 승인자
    private String payDate;           // 지급일시
    private String payUser;           // 지급자
    
    // 슬롯 정보 (동적 급여항목)
    private String slotLabelJson;     // 급여항목명 JSON
    private String slotValueJson;     // 급여항목값 JSON
    
    // 기본 생성자
    public SalaryListVO() {}
    
    // Getter/Setter 메서드들
    public String getSalaryYear() {
        return salaryYear;
    }
    
    public void setSalaryYear(String salaryYear) {
        this.salaryYear = salaryYear;
    }
    
    public String getSalaryMonth() {
        return salaryMonth;
    }
    
    public void setSalaryMonth(String salaryMonth) {
        this.salaryMonth = salaryMonth;
    }
    
    public String getEmpName() {
        return empName;
    }
    
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getSalaryId() {
        return salaryId;
    }
    
    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }
    
    public String getEmpNo() {
        return empNo;
    }
    
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
    
    public String getDeptName() {
        return deptName;
    }
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    public String getPositionName() {
        return positionName;
    }
    
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    
    public BigDecimal getBaseSalary() {
        return baseSalary;
    }
    
    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }
    
    public BigDecimal getNetSalary() {
        return netSalary;
    }
    
    public void setNetSalary(BigDecimal netSalary) {
        this.netSalary = netSalary;
    }
    
    public String getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    
    public String getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    public String getApproveDate() {
        return approveDate;
    }
    
    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }
    
    public String getApproveUser() {
        return approveUser;
    }
    
    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }
    
    public String getPayDate() {
        return payDate;
    }
    
    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
    
    public String getPayUser() {
        return payUser;
    }
    
    public void setPayUser(String payUser) {
        this.payUser = payUser;
    }
    
    public String getSlotLabelJson() {
        return slotLabelJson;
    }
    
    public void setSlotLabelJson(String slotLabelJson) {
        this.slotLabelJson = slotLabelJson;
    }
    
    public String getSlotValueJson() {
        return slotValueJson;
    }
    
    public void setSlotValueJson(String slotValueJson) {
        this.slotValueJson = slotValueJson;
    }
    
    @Override
    public String toString() {
        return "SalaryListVO{" +
                "salaryYear='" + salaryYear + '\'' +
                ", salaryMonth='" + salaryMonth + '\'' +
                ", empName='" + empName + '\'' +
                ", status='" + status + '\'' +
                ", salaryId='" + salaryId + '\'' +
                ", empNo='" + empNo + '\'' +
                ", deptName='" + deptName + '\'' +
                ", positionName='" + positionName + '\'' +
                ", baseSalary=" + baseSalary +
                ", netSalary=" + netSalary +
                ", createDate='" + createDate + '\'' +
                ", createUser='" + createUser + '\'' +
                ", approveDate='" + approveDate + '\'' +
                ", approveUser='" + approveUser + '\'' +
                ", payDate='" + payDate + '\'' +
                ", payUser='" + payUser + '\'' +
                ", slotLabelJson='" + slotLabelJson + '\'' +
                ", slotValueJson='" + slotValueJson + '\'' +
                '}';
    }
} 