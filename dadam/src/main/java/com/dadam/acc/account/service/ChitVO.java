package com.dadam.acc.account.service;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChitVO {
	
    private String chitCode;       // 전표코드(FK)
    private String chitTitle;      // 전표타이틀
    private String deptCode;       // 등록부서
    private String phyEmpId;       // 담당자(FK)
    private String apprEmpId;      // 승인자(FK)
    private String status;         // 상태
    private String note;           // 비고
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date createdDate;      // 등록일자
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date updateDate;       // 수정일자
    private String taxCode;        // 세금계산서번호
    private String vdrCode;        // 거래처코드
    private String articleCode;    // 항목코드(FK)
    private String ttype;           // 구분
    private Double supplyPrice;    // 공급가액
    private Double vatPrice;       // 부가세
    private Double totPrice;       // 총금액
    private String comId;          // 회사ID(FK)
    
    //분개테이블
    private String acctId;  	   //계정과목
    private String iType;
    private Double iPrice;
    private String acctCode;
    private String name;
    private String chitDtId;
    private String empId;
   
   
    
    private List<ChitVO> createdRows;
    private List<ChitVO> updatedRows;
    private List<ChitVO> deletedRows;
    
     
}
