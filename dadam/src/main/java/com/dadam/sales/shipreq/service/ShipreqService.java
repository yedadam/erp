package com.dadam.sales.shipreq.service;

import java.util.List;

public interface ShipreqService {
	public List<ShipReqVO> findShipreqList(); //출하의뢰리스트
	public List<ShipReqDtlVO> findShipreqDtlList(String shipReqCode);
	public int  insertShipreqReg(ShipReqFrontVO req); //등록할때 헤더,디테일 등록하기
	public int updateShiPExpDate(ShipReqVO head);  // 납기일자 변경하기 
}
 