package com.dadam.sales.order.service;

import java.util.List;
import java.util.Map;

public interface OrderService {
		public List<OrdersVO> findOrderList(Map<String,Object> map);  //주문리스트전체조회
		public List<OrdDtlVO> findOrdListByOrdNo(String ordCode);//주문상세건 조회
	    public int orderInsert(OrdReqVO req); //주문 insert 
	    public int ordDtlInsert(OrdReqVO req); 
	    public int removeOrders(String ordCode);//주문삭제 
	    public int updOrder(OrdersVO ord); //주문수정 
	    public int updOrdDtl(OrdDtlVO dtl); //주문 디테일 
	    public int deleteOrdDtl(String ordDtlCode); //주문상세삭제 
	    
	    
}
