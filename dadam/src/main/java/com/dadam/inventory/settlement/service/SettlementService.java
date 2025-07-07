package com.dadam.inventory.settlement.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dadam.security.service.EmployeesVO;

public interface SettlementService {
	public List<EmployeesVO> settleEmployeesList();
	
	public List<SettlementVO> autoSettlementList();
	
	public String settlementAdd(List<SettlementVO> vo);
	
	public int eleAdd(List<SettlementVO> vo);
	
	public List<SettlementVO> settleList(String type,String value);
	
	public List<SettlementVO> mineEle();
	
	public void prcEletronicUpdate(SettlementVO vo);
	
	public int monthCheck();
	
	public int eleList();
}
