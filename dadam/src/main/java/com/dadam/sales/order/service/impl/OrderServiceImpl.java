package com.dadam.sales.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.sales.order.mapper.OrderMapper;
import com.dadam.sales.order.service.OrdDtlVO;
import com.dadam.sales.order.service.OrdReqVO;
import com.dadam.sales.order.service.OrderService;
import com.dadam.sales.order.service.OrdersVO;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMapper orderMapper;

	// 전체조회
	@Override
	public List<OrdersVO> findOrderList() {
		List<OrdersVO> result = orderMapper.findOrderList();
		return result;
	}

	@Override
	public List<OrdDtlVO> findOrdListByOrdNo(String ordCode) {
		List<OrdDtlVO> result = orderMapper.findOrdListByOrdNo(ordCode);
		return result;
	}

	@Transactional
	@Override
	public int orderInsert(OrdReqVO ord) {
		// 헤더 등록
		orderMapper.orderInsert(ord.getOrd());

		String vdrCode = ord.getOrd().getVdrCode(); // vdrCode 거래처코드
		Long totPrice = ord.getOrd().getTotPrice(); // totPrice
		// 외상매입금일경우 여신차감
		if (ord.getOrd().getPayMethod().equals("opm01")) {
			orderMapper.updateCreditBal(totPrice, vdrCode);
		}

		return 0;
	}

	@Override
	public int removeOrders(String ordCode) {
		orderMapper.deleteOrders(ordCode); // 주문삭제
		return 0;
	}

	@Override
	public int updOrder(OrdersVO ord) {
		orderMapper.updOrder(ord); // 주문수정하기
		return 0;
	}

	@Override
	public int updOrdDtl(OrdDtlVO dtl) {
		orderMapper.updOrdDtl(dtl);
		return 0;
	}

	@Override
	public int deleteOrdDtl(String ordDtlCode) {
		orderMapper.deleteOrdDtl(ordDtlCode);
		return 0;
	}
	@Transactional
	@Override
	public int ordDtlInsert(OrdReqVO req) {
		for (int i = 0; i < req.getDtl().getCreatedRows().size(); i++) {
			orderMapper.odtlInsert(req.getDtl().getCreatedRows().get(i));
		}
		return 0;
	}

}