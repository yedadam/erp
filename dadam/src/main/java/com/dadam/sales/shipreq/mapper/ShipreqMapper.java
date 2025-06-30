package com.dadam.sales.shipreq.mapper;

import java.util.List;

import com.dadam.sales.shipreq.service.ShipReqDtlVO;
import com.dadam.sales.shipreq.service.ShipReqVO;

public interface ShipreqMapper {
	public List<ShipReqVO> findShipreqList(); //출하의뢰 리스트조회
	public List<ShipReqDtlVO> findShipreqDtlList(String shipReqCode); //출하의뢰코드로 상세조회
	
}
