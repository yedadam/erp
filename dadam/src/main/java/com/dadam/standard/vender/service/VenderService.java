package com.dadam.standard.vender.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;


public interface VenderService {
     
	public List<VenderVO> venderFindAll(String type,String value); 
	public List<VenderVO> venderFindAllList(String type,String value);
	int insertVender(VenderVO vender);
	int updateVender(VenderVO vender); //거래처
	int  deleteVender(String vdrCode); //거래처삭제 
	String findVenderMaxno(); //거래처번호 최대값  
	
}
