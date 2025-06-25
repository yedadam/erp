package com.dadam.sales.purchase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.dadam.sales.purchase.mapper.PurchaseMapper;
import com.dadam.sales.purchase.service.PurchaseOrderVO;
import com.dadam.sales.purchase.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService{
	@Autowired
	PurchaseMapper mapper;
	//전체조회
	@Override
	public List<PurchaseOrderVO> findPurchaseList(PurchaseOrderVO vo) {
		List<PurchaseOrderVO> result = mapper.findPurchaseList(vo);
		return result;
	}
	//단건조회
	@Override
	public List<PurchaseOrderVO> findPurListByOrdNo(String param) {
		 List<PurchaseOrderVO> result = mapper.findPurListByOrdNo(param);
		return result;
	}
}
