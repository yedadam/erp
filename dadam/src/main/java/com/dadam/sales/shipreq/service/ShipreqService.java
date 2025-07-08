package com.dadam.sales.shipreq.service;

import java.util.List;
import java.util.Map;

public interface ShipreqService {
	public List<ShipReqVO> findShipreqList(Map<String,Object> map); //출하의뢰리스트
	public List<ShipReqDtlVO> findShipreqDtlList(String shipReqCode);
	public int  insertShipreqReg(ShipReqFrontVO req); //등록할때 헤더,디테일 등록하기
	public int updateShiPExpDate(List<ShipReqVO> list);  // 납기일자 변경하기 
	public int deleteShipReq(ShipReqFrontVO req); //클릭하고 삭제시 주문상태 업데이트,디테일삭제,헤더삭제 
	public int deleteShipReqDtlBydtlno(ShipReqFrontVO req);
}
 