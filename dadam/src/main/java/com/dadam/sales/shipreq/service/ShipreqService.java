package com.dadam.sales.shipreq.service;

import java.util.List;

public interface ShipreqService {
	public List<ShipReqVO> findShipreqList(); //출하의뢰리스트
	public List<ShipReqDtlVO> findShipreqDtlList(String shipReqCode);
}
