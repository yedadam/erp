package com.dadam.inventory.inbound.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.inventory.inbound.mapper.InboundMapper;
import com.dadam.inventory.inbound.service.InboundService;
import com.dadam.inventory.inbound.service.InboundVO;
import com.dadam.inventory.inbound.service.PurchaseVO;

@Transactional
@Service
public class InboundServiceImpl implements InboundService{
	
	@Autowired
	InboundMapper inboundMapper;
	
	// 입고 리스트 
	@Override
	public List<InboundVO> selectPurchaseList(String comId) {
		List<InboundVO> list = inboundMapper.selectPurchaseList(comId);
		return list;
	}
	
	// 발주서 리스트
	@Override
	public List<PurchaseVO> inboundPurchaseFindAll(PurchaseVO vo) {
		List<PurchaseVO> list = inboundMapper.inboundPurchaseFindAll(vo);
		return list;
	}
	// 입고 등록
	@Override
	public int insertPurchaseInbound(List<PurchaseVO> list) {
		Map<String, String> map = new HashMap<>();
		int result = 0;
		System.out.println("list:" + list);
		for (PurchaseVO vo : list) {
			// 입고항목 등록
			// 현재수량에 입력한 재고와 현재수량을 더해서 넣음.
			vo.setCurrQty(vo.getCurrQty() + vo.getQuantity());
			result = inboundMapper.insertPurchaseInbound(vo);
			inboundMapper.updateStockInbound(vo);
			// 기본은 입고 완료
			String status = "pds03"; // 입고 완료
			// 수량부족하면 부분 입고
			if("pct01".equals(vo.getStatus())) {
				status = "pds02"; // 부분 입고
			}
			// 품목 중복값을 제거하기위해서 map을 사용
			map.put(vo.getPurOrdDtlCode(), status);
		}
		// 재고 항목 머지문
		// for문조건으로 entrySet을 사용해서 map의 모든값(key, value)을 꺼내서
		for (Map.Entry<String, String> entry : map.entrySet()) {
			PurchaseVO vo = new PurchaseVO();
			vo.setComId(list.get(0).getComId());
			vo.setPurOrdDtlCode(entry.getKey());
			vo.setStatus(entry.getValue());
			System.out.println("vo:" + vo);
			inboundMapper.updatePurchaseOrderDetailInbound(vo);
			inboundMapper.prcPurchaseOrderStatus(vo);
		}
		System.out.println("result:" + result);
		return result;
	}
	// 창고 리스트
	@Override
	public List<InboundVO> warehouseList(String comId) {
		List<InboundVO> list = inboundMapper.warehouseList(comId);
		return list;
	}
	// 현재수량
	@Override
	public List<PurchaseVO> purchaseCurrQty(List<PurchaseVO> list) {
		/*
		 * List<Integer> lists; for(List<String> vo : list) { lists =
		 * inboundMapper.purchaseCurrQty(vo); }
		 */
		return null;
	}
}
