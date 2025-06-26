package com.dadam.sales.purchase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.sales.purchase.mapper.PurchaseMapper;
import com.dadam.sales.purchase.service.PurReqVO;
import com.dadam.sales.purchase.service.PurchaseOrderDetailVO;
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
	public List<PurchaseOrderDetailVO> findPurListByOrdNo(String param) {
		 List<PurchaseOrderDetailVO> result = mapper.findPurListByOrdNo(param);
		return result;
	}
	//발주의뢰 코드보기
	@Override
	public List<PurchaseOrderVO> requestList() {
		List<PurchaseOrderVO> result = mapper.requestList();
		return result;		
	}
	//발주의뢰 상세코드
	@Override
	public List<PurchaseOrderVO> requestDeatilList(String param) {
		List<PurchaseOrderVO> result = mapper.requestDeatilList(param);
		return result;
	}
	//발주 전체 등록
	@Transactional
	@Override
	public int purchaseOrderAdd(PurReqVO param) {
		System.out.println(param);
		//헤더등록
		int result = mapper.purchaseOrderAdd(param.getPur());
		//상태값 변경
		 if(param.getPur().getPurReqCode() != null && !param.getPur().getPurReqCode().isEmpty()) {
			 mapper.reqStatusUpdate("prs02", param.getPur().getPurReqCode());
		 }
		
		//디테일등록
		param.getDtl().forEach(item -> {
			System.out.println(param.getPur().getPurOrdCode());
			item.setPurOrdCode(param.getPur().getPurOrdCode());
			mapper.purchaseOrderDetailAdd(item);
		});
		return result;
	}
	
	//발주 메인 업데이트
	@Override
	public int purOrderUpdate(PurchaseOrderVO param) {
		int result = mapper.purOrderUpdate(param);
		return result;
	}
	
	//발주 상세 업데이트
	@Override
	public int purOderDtUpdate(PurchaseOrderDetailVO param) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
