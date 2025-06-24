package com.dadam.hr.emp.service;

import lombok.Data;
import java.util.Date;

@Data
public class DeptVO {
    private String deptCode;
    private String deptName;
    private String parentDeptCode;
    private String empId;
    private Date createdDate;
    private String comId;
}
 