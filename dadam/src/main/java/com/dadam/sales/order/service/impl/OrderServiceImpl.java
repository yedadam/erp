package com.dadam.sales.order.service.impl;

import java.util.List;
import java.util.Map;

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
	public List<OrdersVO> findOrderList(Map<String,Object> map) {
		initAuthInfo();
		map.put("comId", comId);	
		List<OrdersVO> result = orderMapper.findOrderList(map);
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
		
		String ordCode=ord.getOrd().getOrdCode();
		
		orderMapper.orderInsert(ord.getOrd()); //헤더 제일먼저 insert 
		
		String vdrCode = ord.getOrd().getVdrCode(); // vdrCode 거래처코드
		Long totPrice = ord.getOrd().getTotPrice(); // totPrice
		// 외상매입금일경우 여신차감-> 모든 지불방식인 경우에 여신잔량 깎는걸로 변경 
		orderMapper.updateCreditBal(totPrice, vdrCode,comId);
		
		ord.getDtl().getUpdatedRows().get(0).setComId(comId);
		ord.getDtl().getUpdatedRows().get(0).setOrdCode(ordCode);     //ordCode세팅 해주기
		orderMapper.odtlInsert(ord.getDtl().getUpdatedRows().get(0)); //등록할때 상세의 제일 첫번째행 
		for(int i=0; i<ord.getDtl().getCreatedRows().size();i++) {
			ord.getDtl().getCreatedRows().get(i).setComId(comId);
			ord.getDtl().getCreatedRows().get(i).setOrdCode(ordCode);
			orderMapper.odtlInsert(ord.getDtl().getCreatedRows().get(i));
		}
		
		
		return 0;
	}


	@Override
	public int removeOrders(String ordCode) {
		initAuthInfo(); 
		orderMapper.callUpdateCreditBalanceIfOpm(ordCode); //주문삭제후 해당 거래처의 여신잔량을 주문금액 만큼 + 처리하게함
		orderMapper.deleteOrders(ordCode,comId); // 주문헤더삭제
		orderMapper.deleteOrdersDtl(ordCode, comId); //주문디테일 삭제
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
		// 상세 삭제하고 나면 주문금액만큼 order 테이블에 가격 만큼 -,수량,공급가액,부가세,총가격,할인가격 싹다 update 처리 되야함  
		
		orderMapper.deleteOrdDtl(ordDtlCode,comId);
		
		
		return 0;
	}		
	@Override
	public int ordDtlInsert(OrdReqVO req) {
		//상세저장 할경우
		initAuthInfo();
		//새로추가된 주문행
		String ordCode=req.getOrd().getOrdCode(); //주문코드헤더에서 받아옴 
		System.out.println("주문번호"+ordCode);
		
		//새로 생긴행 
		for (int i = 0; i < req.getDtl().getCreatedRows().size(); i++) {
			req.getDtl().getCreatedRows().get(i).setComId(comId); //comId 확인하기 
			req.getDtl().getCreatedRows().get(i).setOrdCode(ordCode); //디테일에 ordCode 부여하기 
			orderMapper.odtlInsertDetailSave(req.getDtl().getCreatedRows().get(i)); 
		}
		
		//주문등록할때 updated된 행도 수정해줘야함 
		for (int i = 0; i < req.getDtl().getUpdatedRows().size(); i++) {
			req.getDtl().getUpdatedRows().get(i).setComId(comId); //comId 확인하기 
			req.getDtl().getUpdatedRows().get(i).setOrdCode(ordCode); //주문번호 부여해주기
			orderMapper.updOrdDtl(req.getDtl().getUpdatedRows().get(i)); 
		}
		//ordersDetail 테이블에 부여받은 ordCode로 insert확인후 총수량,총가격,총부가세,총 공급가액 을 바꾸는 프로시저 실행
		orderMapper.callUpdateOrderTotals(ordCode);  	
		List<OrdDtlVO> list=orderMapper.findOrdListByOrdNo(ordCode);
		Long total=0L;
		//주문건 상세조회후 수정된 총금액 조회
		for(int i=0;i<list.size();i++) {
			total+=orderMapper.findOrdListByOrdNo(ordCode).get(i).getTotPrice(); //totPrice 계산 
		}
		//주문수정후 여신잔량을 바꿈 
		orderMapper.callPrcCreditBalanceForModify(ordCode, comId,total);
		System.out.println("상세저장무사히 완료");
		return 0;
	}

}