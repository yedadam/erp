package com.dadam.sales.order.service;

import java.util.List;

public interface OrderService {
		public List<OrdersVO> findOrderList();  //주문리스트전체조회
		public List<OrdDtlVO> findOrdListByOrdNo(String ordCode);//주문상세건 조회
	    public int orderInsert(OrdReqVO req); //주문 insert  
	    public int removeOrders(String ordCode);//주문삭제 
	    public int updOrder(OrdersVO ord); //주문수정 
}
