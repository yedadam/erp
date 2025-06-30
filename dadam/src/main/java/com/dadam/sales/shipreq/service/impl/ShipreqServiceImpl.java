package com.dadam.sales.shipreq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.sales.shipreq.mapper.ShipreqMapper;
import com.dadam.sales.shipreq.service.ShipReqDtlVO;
import com.dadam.sales.shipreq.service.ShipReqFrontVO;
import com.dadam.sales.shipreq.service.ShipReqVO;
import com.dadam.sales.shipreq.service.ShipreqService;
@Service
@Transactional
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

	@Override
	
	public int insertShipreqReg(ShipReqFrontVO req) {
		shipreqMapper.insertShipreqHead(req.getHead()); //헤더먼저등록
		System.out.println("가가가가가");  
		
		
		String shipreqCode=req.getHead().getShipReqCode(); 
		System.out.println(shipreqCode);
		System.out.println(req.getDtl());
		for(int i=0;i<req.getDtl().size(); i++ ) {
		  req.getDtl().get(i).setShipReqCode(shipreqCode); //shipreqCode로 지정
		  System.out.println("ㅇㅇㅇㅇㅇ"); 
		  shipreqMapper.insertShipreqDtl(req.getDtl().get(i)); //디테일 for문 돌리면서 등록 
		}
		return 0;
	}  
}
