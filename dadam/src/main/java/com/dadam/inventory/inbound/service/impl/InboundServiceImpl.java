package com.dadam.inventory.inbound.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.inventory.inbound.mapper.InboundMapper;
import com.dadam.inventory.inbound.service.InboundService;
import com.dadam.inventory.inbound.service.InboundVO;
import com.dadam.inventory.inbound.service.PurchaseVO;
import com.dadam.sales.purchase.service.impl.PurchaseServiceImpl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Transactional
@Service
public class InboundServiceImpl implements InboundService{
	
	@Autowired
	InboundMapper inboundMapper;
	
	// 발주서 리스트
	@Override
	public List<PurchaseVO> inboundPurchaseFindAll(PurchaseVO vo) {
		List<PurchaseVO> list = inboundMapper.inboundPurchaseFindAll(vo);
		return list;
	}
	// 입고 등록
	@Override
	public void insertPurchaseInbound(List<PurchaseVO> list) {
		Map<String, String> map = new HashMap<>();
		for (PurchaseVO vo : list) {
			// 입고항목 등록
			inboundMapper.insertPurchaseInbound(vo);
			// 재고 항목 머지문
			inboundMapper.updateStockInbound(vo);
			String status = "pds-03"; // 입고 완료
			if("pct-01".equals(vo.getStatus())) {
				status = "pds-02"; // 부분 입고
			}
			// 품목 중복값을 제거하기위해서 map을 사용
			map.put(vo.getPurOrdDtlCode(), status);
		}
		// for문조건으로 entrySet을 사용해서 map의 모든값(key, value)을 꺼내서 
		for (Map.Entry<String, String> entry : map.entrySet()) {
			PurchaseVO vo = new PurchaseVO();
			vo.setPurOrdDtlCode(entry.getKey());
			vo.setStatus(entry.getValue());
			inboundMapper.updatePurchaseOrderDetailInbound(vo);
		}
		
	}
	// 창고 리스트
	@Override
	public List<InboundVO> warehouseList(String comId) {
		List<InboundVO> list = inboundMapper.warehouseList(comId);
		return list;
	}
	
}
