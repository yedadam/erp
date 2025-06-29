package com.dadam.hr.emp.service;

import lombok.Data;
import java.util.Date;

@Data
public class DeptVO {
    /** 부서코드 */
    private String deptCode;
    /** 부서명 */
    private String deptName;
    /** 상위부서코드 */
    private String parentDeptCode;
    /** 부서장 사원번호 */
    private String empId;
    /** 생성일 */
    private Date createdDate;
    /** 회사ID */
    private String comId;
}
 