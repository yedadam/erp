package com.dadam.sales.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.sales.order.mapper.OrderMapper;
import com.dadam.sales.order.service.OrdDtlVO;
import com.dadam.sales.order.service.OrdReqVO;
import com.dadam.sales.order.service.OrderService;
import com.dadam.sales.order.service.OrdersVO;
import com.dadam.security.service.LoginUserAuthority;
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
	
	 String comId = "com-101";
	    public void initAuthInfo() {
	        //로그인 객체값 연결
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        //로그인 객체 가져오기
	        Object principal = auth.getPrincipal();

	        if (principal instanceof LoginUserAuthority) {
	        	LoginUserAuthority user = (LoginUserAuthority) principal;
	            comId = user.getComId();
	            System.out.println("회사명: " + comId);
	        }
	    }

	@Autowired
	OrderMapper orderMapper;

	// 전체조회
	@Override
	public List<OrdersVO> findOrderList(String type,String value) {
		List<OrdersVO> result = orderMapper.findOrderList(type,value,comId);
		return result;
	}

	@Override
	public List<OrdDtlVO> findOrdListByOrdNo(String ordCode) {
		List<OrdDtlVO> result = orderMapper.findOrdListByOrdNo(ordCode);
		return result;
	}

	@Override
	public int orderInsert(OrdReqVO ord) {
		// 헤더 등록
		
		ord.getOrd().setComId(comId);
		orderMapper.orderInsert(ord.getOrd());
		
		String vdrCode = ord.getOrd().getVdrCode(); // vdrCode 거래처코드
		Long totPrice = ord.getOrd().getTotPrice(); // totPrice
		// 외상매입금일경우 여신차감
		
			orderMapper.updateCreditBal(totPrice, vdrCode,comId);

		return 0;
	}

	@Override
	public int removeOrders(String ordCode) {
		orderMapper.callUpdateCreditBalanceIfOpm(ordCode); //주문삭제후 해당 거래처의 여신잔량을 주문금액 만큼 + 처리하게함
		orderMapper.deleteOrders(ordCode,comId); // 주문삭제
		return 0;
	}

	@Override
	public int updOrder(OrdersVO ord) {
		ord.setComId(comId);
		orderMapper.updOrder(ord); // 주문수정하기
		return 0;
	}

	@Override
	public int updOrdDtl(OrdDtlVO dtl) {
		String ordCode=dtl.getOrdCode(); // ord-101 프론트에서 ordCode 받아와야 함 
		System.out.println("ordCode 잘 받아오는지 확인하기 !!!!"+ordCode);
		dtl.setComId(comId);
		orderMapper.updOrdDtl(dtl); //단건 주문 디테일 수정함
		orderMapper.callUpdateOrderTotals(ordCode); //단건 수정한뒤에 주문건 헤더 update  TOT_SUP_PRICE,TOT_VAT_PRICE,TOT_DISC,TOT_PRICE,TOT_QUANTITY
		return 0;
	}

	@Override
	public int deleteOrdDtl(String ordDtlCode) {
		orderMapper.deleteOrdDtl(ordDtlCode,comId);	
		return 0;
	}		
	@Override
	public int ordDtlInsert(OrdReqVO req) {
		for (int i = 0; i < req.getDtl().getCreatedRows().size(); i++) {
			orderMapper.odtlInsert(req.getDtl().getCreatedRows().get(i));
		}
		return 0;
	}

}