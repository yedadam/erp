package com.dadam.sales.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dadam.sales.order.service.OrdDtlVO;
import com.dadam.sales.order.service.OrdersVO;

public interface OrderMapper {
        public List<OrdersVO> findOrderList(Map<String,Object> map); //주문건조회  
        public List<OrdDtlVO> findOrdListByOrdNo(String ordCode);//주문건당 상세내역  
        public int orderInsert(OrdersVO ord); //주문헤더 insert
        public int odtlInsert(OrdDtlVO dtl); //주문Dtl insert 
        public int updVdrcreditBalPrice( @Param("vdrCode") String vdrCode,@Param ("totPrice") Long totPrice,@Param("comId")String comId);
        // 프로시저 호출
        
        
        void callUpdateCreditBal(@Param("vdrId") String vdrId, @Param("totPrice") Long totPrice); //여신잔량 계산 
        public int deleteOrders(@Param("ordCode")String ordCode,@Param("comId") String comId);//주문삭제 
        public int updOrder(OrdersVO ord); //주문헤더수정
        public int updOrdDtl(OrdDtlVO ordDtl); //주문상세 수정 
        public int deleteOrdDtl(@Param("ordDtlCode")String ordDtlCode,@Param("comId") String comId); //주문상세 삭제 
        public int updateCreditBal(@Param("totPrice") Long totPrice,@Param("vdrCode") String vdrCode,@Param("comId")String comId); 
        public void callUpdateOrderTotals(@Param("ordCode") String ordCode); //주문상세 변경후 전체
        public void callUpdateCreditBalanceIfOpm(@Param("ordCode") String ordCode ); //주문상세 삭제하면 거래처의 여신잔량이 삭제된 금액만큼 update처리됨 
        public void callPrcCreditBalanceForModify(@Param("ordCode")String ordCode,@Param("comId")String comId,@Param("total") Long total); //주문수정후 거래처 여신잔량 변경
        
}
