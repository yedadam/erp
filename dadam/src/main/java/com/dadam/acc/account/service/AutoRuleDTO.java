package com.dadam.acc.account.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class AutoRuleDTO {
    @JsonProperty("chitType")
    private String chitType;

    @JsonProperty("itpType")
    private String itpType;

    @JsonProperty("acctCode")
    private String acctCode;
    
    @JsonProperty("vatYn")
    private String vatYn;
    
}
