package com.dadam.sales.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.sales.order.mapper.OrderMapper;
import com.dadam.sales.order.service.OrdDtlVO;
import com.dadam.sales.order.service.OrderService;
import com.dadam.sales.order.service.OrdersVO;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderMapper orderMapper;
	
	//전체조회 
	@Override
	public List<OrdersVO> findOrderList() {
		List<OrdersVO> result=orderMapper.findOrderList();  
		return result;
	}

	@Override
	public List<OrdDtlVO> findOrdListByOrdNo(String ordCode) {
		List<OrdDtlVO> result=orderMapper.findOrdListByOrdNo(ordCode); 
		return result;
	} 
	
	
}
