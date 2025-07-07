package com.dadam.inventory.settlement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dadam.inventory.settlement.service.SettlementVO;
import com.dadam.security.service.EmployeesVO;

public interface SettlementMapper {
   
	public List<EmployeesVO> settleEmployeesList(@Param("comId") String comId);
	
	public List<SettlementVO> autoSettlementList(@Param("comId") String comId);
	
	public int settlementMainAdd(SettlementVO vo);

	public int settlementDtlAdd(SettlementVO vo);
	
	public int eleAdd(SettlementVO vo);
	
	public  List<SettlementVO> settleList(@Param("type") String type,@Param("value") String value,@Param("comId") String comId);
	
	public List<SettlementVO> mineEle(@Param("comId") String comId,@Param("appId") String appId);
	
	public void prcEletronicUpdate(SettlementVO vo);
	
	public int monthCheck(@Param("comId") String comId);
	
	public int eleList(@Param("comId") String comId,@Param("empId") String empId);
}
