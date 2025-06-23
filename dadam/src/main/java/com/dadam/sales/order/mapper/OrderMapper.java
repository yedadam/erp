package com.dadam.sales.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dadam.sales.order.service.OrdDtlVO;
import com.dadam.sales.order.service.OrdersVO;

public interface OrderMapper {
        public List<OrdersVO> findOrderList(); //주문건조회  
        public List<OrdDtlVO> findOrdListByOrdNo(String ordCode);//주문건당 상세내역  
        public int orderInsert(OrdersVO ord); //주문헤더 insert
        public int odtlInsert(OrdDtlVO dtl); 
        public int updVdrcreditBalPrice( @Param("vdrCode") String vdrCode,@Param ("totPrice") Long totPrice);
        // 프로시저 호출
        void callUpdateCreditBal(@Param("vdrId") String vdrId, @Param("totPrice") Long totPrice); 
        
           
}
