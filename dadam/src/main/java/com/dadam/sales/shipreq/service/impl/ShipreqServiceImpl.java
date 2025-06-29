package com.dadam.sales.shipreq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.sales.shipreq.mapper.ShipreqMapper;
import com.dadam.sales.shipreq.service.ShipReqDtlVO;
import com.dadam.sales.shipreq.service.ShipReqVO;
import com.dadam.sales.shipreq.service.ShipreqService;

@Service
public class ShipreqServiceImpl implements ShipreqService {
	@Autowired
	ShipreqMapper shipreqMapper;

	@Override
	public List<ShipReqVO> findShipreqList() {
		List<ShipReqVO> result=shipreqMapper.findShipreqList(); 
		return result;
	}

	@Override
	public List<ShipReqDtlVO> findShipreqDtlList(String shipReqCode) {
		List<ShipReqDtlVO> result=shipreqMapper.findShipreqDtlList(shipReqCode); 
		return result;
	} 
}
