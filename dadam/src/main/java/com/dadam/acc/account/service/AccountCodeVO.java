package com.dadam.acc.account.service;

import lombok.Data;

@Data
public class AccountCodeVO {
	//대분류
    private String typeCode;
    private String typeName;
    private String typeNote; // 대분류의 비고

    //중분류
    private String classCode;
    private String className;
    private String classNote; // 중분류의 비고

    //소분류
    private String subclassCode;
    private String subclassName;
    private String subclassNote; // 소분류의 비고

    //계정과목
    private String accountName;
    private String accountNote; // 계정과목(account)의 비고
}
