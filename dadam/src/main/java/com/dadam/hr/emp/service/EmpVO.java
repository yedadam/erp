package com.dadam.hr.emp.service;

import java.util.Date;
import lombok.Data;

@Data
public class EmpVO {
    private String empId;
    private String comId;
    private String deptCode;
    private String pwd;
    private String empName;
    private String email;
    private String tel;
    private Date hireDate;
    private String position;
    private Date resignDate;
    private String empStatus;
    private String workType;
    private String profileImgPath;
    private String note;
    private Integer sal;
    private String bank;
    private String acctNo;
    private Date birthDate;
    private String resignReason;
    private String addr;

    // DEPARTMENTS 테이블과 JOIN하여 부서명을 담을 필드
    private String deptName;
} 