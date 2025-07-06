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
		initAuthInfo(); 
		List<OrdersVO> result = orderMapper.findOrderList(type,value,comId);
		return result;
	}

	@Override
	public List<OrdDtlVO> findOrdListByOrdNo(String ordCode) {
		initAuthInfo(); 
		List<OrdDtlVO> result = orderMapper.findOrdListByOrdNo(ordCode);
		return result;
	}

	@Override
	public int orderInsert(OrdReqVO ord) {
		initAuthInfo(); 
		ord.getOrd().setComId(comId);
	
		orderMapper.orderInsert(ord.getOrd());
		
		String vdrCode = ord.getOrd().getVdrCode(); // vdrCode 거래처코드
		Long totPrice = ord.getOrd().getTotPrice(); // totPrice
		// 외상매입금일경우 여신차감-> 모든 지불방식인 경우에 여신잔량 깎는걸로 변경 
		orderMapper.updateCreditBal(totPrice, vdrCode,comId);

		return 0;
	}

	@Override
	public int removeOrders(String ordCode) {
		initAuthInfo(); 
		orderMapper.callUpdateCreditBalanceIfOpm(ordCode); //주문삭제후 해당 거래처의 여신잔량을 주문금액 만큼 + 처리하게함
		orderMapper.deleteOrders(ordCode,comId); // 주문삭제
		return 0;
	}

	@Override
	public int updOrder(OrdersVO ord) {
		initAuthInfo();
		ord.setComId(comId);
		orderMapper.updOrder(ord); // 주문수정하기
		return 0;
	}

	@Override
	public int updOrdDtl(OrdDtlVO dtl) {
		initAuthInfo();
		String ordCode=dtl.getOrdCode(); // ord-101 프론트에서 ordCode 받아와야 함 
		System.out.println("ordCode 잘 받아오는지 확인하기 !!!!"+ordCode);
		dtl.setComId(comId);
		Long  total=0L;
		for(int i=0;i<orderMapper.findOrdListByOrdNo(ordCode).size();i++) {
			total+=orderMapper.findOrdListByOrdNo(ordCode).get(i).getTotPrice();
			System.out.println(orderMapper.findOrdListByOrdNo(ordCode).get(i).getTotPrice()); //기존 디테일 가격들을 더함 => 
		}
		System.out.println("원래 주문한 total=>"+total); //기존 order 테이블의 총금액이됨 
		
		orderMapper.updOrdDtl(dtl); //단건 주문 디테일 수정함
		orderMapper.callUpdateOrderTotals(ordCode); //단건 수정한뒤에 주문건 헤더 update  총가격,총부가세,총할인,총수량
		orderMapper.callPrcCreditBalanceForModify(ordCode, comId,total); //주문수정하면 거래처 여신잔량도 update됨  
		return 0;
	}

	@Override
	public int deleteOrdDtl(String ordDtlCode) {
		initAuthInfo();
		orderMapper.deleteOrdDtl(ordDtlCode,comId);	
		return 0;
	}		
	@Override
	public int ordDtlInsert(OrdReqVO req) {
		initAuthInfo();
		//새로추가된 주문행 
		for (int i = 0; i < req.getDtl().getCreatedRows().size(); i++) {
			req.getDtl().getCreatedRows().get(i).setComId(comId); //comId 확인하기 
			orderMapper.odtlInsert(req.getDtl().getCreatedRows().get(i));
		}
		String ordCode=req.getOrd().getOrdCode(); //주문코드헤더에서 방아옴 
		System.out.println("주문번호"+ordCode);
		//주문등록할때 updated된 행도 주문등록해줘야함 
		for (int i = 0; i < req.getDtl().getUpdatedRows().size(); i++) {
			req.getDtl().getUpdatedRows().get(i).setComId(comId); //comId 확인하기 
			req.getDtl().getUpdatedRows().get(i).setOrdCode(ordCode); //주문번호 부여해주기
			orderMapper.odtlInsert(req.getDtl().getUpdatedRows().get(i));
		}
		return 0;
	}

}