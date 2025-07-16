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
		int sumqty = 0;
		String purdtl = null;
		for (PurchaseVO vo : list) {
			String checkpur = vo.getPurOrdDtlCode();
			// 입고항목 등록
			// 가장 높은 수량을 가져와서 변수저장
			
			// 이 방법은 안됨. 트랜젝션이 닫히지 않은 상태에서 재조회해도 같은결과만 나오기 떄문.
			// inboundMapper.purchaseCurrQty(vo);
			
			// 한종류의 발주상세는 가능한데 여러건이라면 중복되기 때문에 초기화 과정이 필요함.
			// purdtl가 현재 vo의 값과 같다면 또는 널이 아니라면
			if(purdtl != null && checkpur.equals(purdtl)) {
				sumqty += vo.getQty();
				vo.setCurrQty(sumqty);
			}else {
				// 다른 purdtlcode라면 값을 초기화
				sumqty = vo.getQty() + vo.getCurrQty();
				vo.setCurrQty(sumqty);
				
				// null이거나 다른값일떄는 그값을 넣음
				purdtl = vo.getPurOrdDtlCode();
			}

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
		// for문조건으로 entrySet을 사용해서 map의 모든값(key, value)을 꺼내서 활용함
		for (Map.Entry<String, String> entry : map.entrySet()) {
			PurchaseVO vo = new PurchaseVO();
			vo.setComId(list.get(0).getComId());
			vo.setPurOrdDtlCode(entry.getKey());
			vo.setStatus(entry.getValue());
			inboundMapper.updatePurchaseOrderDetailInbound(vo);
			inboundMapper.prcPurchaseOrderStatus(vo);
		}
		return result;
	}
	// 창고 리스트
	@Override
	public List<InboundVO> warehouseList(String comId) {
		List<InboundVO> list = inboundMapper.warehouseList(comId);
		return list;
	}
	// 사용안함
	@Override public List<PurchaseVO> purchaseCurrQty(List<PurchaseVO> list) {
		return null;
	}
	 
}
