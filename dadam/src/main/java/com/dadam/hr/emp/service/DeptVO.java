package com.dadam.hr.emp.service;

import lombok.Data;
import java.util.Date;

/**
 * 부서 VO
 * - 부서 정보 데이터 전달 객체
 */
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
    /** 사용여부 (Y/N) */
    private String acctYn;
    /** 비고 */
    private String remark;
    /** 인원수 */
    private Integer empCount;
    /** 상위부서명(조회용) */
    private String parentDeptName;
    /** 사용여부 한글(조회용) */
    private String statusText;
    /** 권한코드 */
    private String authCode;
    /** 권한명(조회용) */
    private String authName;
    /** 등록일(문자열, 조회용) */
    private String createdDateStr;
}
 