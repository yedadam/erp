package com.dadam.hr.emp.service;

import java.util.Date;

import lombok.Data;

@Data
public class EmpVO {

	// 기본 정보
    private String empId;           // 사원ID (PK)
    private String comId;           // 회사ID
    private String deptCode;        // 부서코드
    private String pwd;             // 패스워드
    private String empName;         // 사원명
    
    // 연락처 정보
    private String email;           // 이메일
    private String tel;             // 전화번호
    private String addr;            // 주소
    
    // 근무 정보
    private Date hireDate;          // 입사일
    private String position;        // 직급
    private Date resignDate;        // 퇴사일
    private String empStatus;       // 재직상태
    private String workType;        // 근무유형
    private String resignReason;    // 퇴사사유
    
    // 급여 정보
    private String sal;             // 급여
    private String bank;            // 은행
    private String acctNo;          // 계좌번호
    
    // 개인 정보
    private Date birthDate;         // 생년월일
    
    // 기타
    private String profileImgPath;  // 프로필 이미지 경로
    private String note;            // 비고
    

}
