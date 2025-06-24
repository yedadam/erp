package com.dadam.hr.emp.service;

import java.util.Date;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class EmpVO {
    private String empId;
    private String comId;
    private String deptCode;
    private String pwd;
    private String empName;
    private String email;
    private String tel;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;
    private String position;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date resignDate;
    private String empStatus;
    private String workType;
    private String profileImgPath;
    private String note;
    private Integer sal;
    private String bank;
    private String acctNo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String resignReason;
    private String addr;

    // DEPARTMENTS 테이블과 JOIN하여 부서명을 담을 필드
    private String deptName;
} 