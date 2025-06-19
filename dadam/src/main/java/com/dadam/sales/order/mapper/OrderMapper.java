package com.dadam.sales.order.mapper;

import java.util.List;

import com.dadam.sales.order.service.OrdDtlVO;
import com.dadam.sales.order.service.OrdersVO;

public interface OrderMapper {
           public List<OrdersVO> findOrderList(); //주문건조회  
           public List<OrdDtlVO> findOrdListByOrdNo(String ordCode);//주문건당 상세내역  
}
