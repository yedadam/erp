package com.dadam.hr.attendance.service;

/**
 * 근태 정보 VO
 * 출근현황, 통계, 그리드용
 */
public class AttendanceVO {
    private String attendanceCode; // 근태코드
    private String empId;         // 사원ID
    private String empName;       // 사원명
    private String deptCode;      // 부서코드
    private String deptName;      // 부서명
    private String workDate;      // 근무일자
    private String checkIn;       // 출근시간
    private String checkOut;      // 퇴근시간
    private String ip;            // 출근IP
    private String status;        // 상태(정상/지각/결근 등)
    private String loc;           // 위치
    private String comId;         // 회사ID

    public String getAttendanceCode() { return attendanceCode; }
    public void setAttendanceCode(String attendanceCode) { this.attendanceCode = attendanceCode; }
    public String getEmpId() { return empId; }
    public void setEmpId(String empId) { this.empId = empId; }
    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }
    public String getDeptCode() { return deptCode; }
    public void setDeptCode(String deptCode) { this.deptCode = deptCode; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    public String getWorkDate() { return workDate; }
    public void setWorkDate(String workDate) { this.workDate = workDate; }
    public String getCheckIn() { return checkIn; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }
    public String getCheckOut() { return checkOut; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getLoc() { return loc; }
    public void setLoc(String loc) { this.loc = loc; }
    public String getComId() { return comId; }
    public void setComId(String comId) { this.comId = comId; }
} 