package com.dadam.sales.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dadam.sales.order.service.OrdDtlVO;
import com.dadam.sales.order.service.OrdersVO;

public interface OrderMapper {
        public List<OrdersVO> findOrderList(); //주문건조회  
        public List<OrdDtlVO> findOrdListByOrdNo(String ordCode);//주문건당 상세내역  
        public int orderInsert(OrdersVO ord); //주문헤더 insert
        public int odtlInsert(OrdDtlVO dtl); //주문Dtl insert 
        public int updVdrcreditBalPrice( @Param("vdrCode") String vdrCode,@Param ("totPrice") Long totPrice);
        // 프로시저 호출
        void callUpdateCreditBal(@Param("vdrId") String vdrId, @Param("totPrice") Long totPrice); //여신잔량 계산 
        public int deleteOrders(String ordCode);//주문삭제 
        public int updOrder(OrdersVO ord); //주문헤더수정
        public int updOrdDtl(OrdDtlVO ordDtl); //주문상세 수정 
        public int deleteOrdDtl(String ordDtlCode); //주문상세 삭제 
}
