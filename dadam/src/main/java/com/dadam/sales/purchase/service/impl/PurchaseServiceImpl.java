package com.dadam.sales.purchase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.common.service.GridData;
import com.dadam.sales.purchase.mapper.PurchaseMapper;
import com.dadam.sales.purchase.service.PurReqVO;
import com.dadam.sales.purchase.service.PurchaseOrderDetailVO;
import com.dadam.sales.purchase.service.PurchaseOrderVO;
import com.dadam.sales.purchase.service.PurchaseService;
import com.dadam.security.service.LoginUserAuthority;
import com.dadam.standard.vender.service.VenderVO;

import jakarta.annotation.PostConstruct;

@Service
public class PurchaseServiceImpl implements PurchaseService{
	
	 
    
    
    //comName 가져오기
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
	PurchaseMapper mapper;
	//전체조회
	@Override
	public List<PurchaseOrderVO> findPurchaseList(PurchaseOrderVO vo) {
		initAuthInfo();
		//검색하지 않았을때
		if(vo == null) {
			vo = new PurchaseOrderVO();
			vo.setComId(comId);
		}
		List<PurchaseOrderVO> result = mapper.findPurchaseList(vo);
		return result;
	}
	//단건조회
	@Override
	public List<PurchaseOrderDetailVO> findPurListByOrdNo(String param) {
		initAuthInfo();
		 List<PurchaseOrderDetailVO> result = mapper.findPurListByOrdNo(param,comId);
		return result;
	}
	//발주의뢰 코드보기
	@Override
	public List<PurchaseOrderVO> requestList(String type,String value) {
		type = type ==null? "" :type;
		value = value ==null? "" :value;
		initAuthInfo();
		List<PurchaseOrderVO> result = mapper.requestList(comId,type,value);
		return result;		
	}
	//발주의뢰 상세코드
	@Override
	public List<PurchaseOrderVO> requestDeatilList(String param) {
		initAuthInfo();
		List<PurchaseOrderVO> result = mapper.requestDeatilList(param,comId);
		return result;
	}
	//거래처 조회
	@Override
	public List<VenderVO> venderList(String type,String value) {
		    type = type == null? "": type;
		    value = value == null? "": value;
			initAuthInfo();
			List<VenderVO> result = mapper.venderList(comId,type,value);
			return result;
	}
	//발주 전체 등록
	@Transactional
	@Override
	public int purchaseOrderAdd(PurReqVO param) {
		initAuthInfo();
		param.getPur().setComId(comId);
		
		//헤더등록
		int result = mapper.purchaseOrderAdd(param.getPur());
		//상태값 변경
		 if(param.getPur().getPurReqCode() != null && !param.getPur().getPurReqCode().isEmpty()) {
			 mapper.reqStatusUpdate("prs02", param.getPur().getPurReqCode(),param.getPur().getComId());
		 }
		
		//디테일등록
		param.getDtl().forEach(item -> {
			item.setComId(comId);
			System.out.println(item.getComId());
			item.setPurOrdCode(param.getPur().getPurOrdCode());
			mapper.purchaseOrderDetailAdd(item);
		});
		return result;
	}
	
	//발주 메인 업데이트
	@Override
	public int purOrderUpdate(PurchaseOrderVO param) {
		initAuthInfo();
		param.setComId(comId);
		int result = mapper.purOrderUpdate(param);
		return result;
	}
	
	//발주 상세 업데이트
	@Override
	@Transactional
	public int purOderDtUpdate(GridData<PurchaseOrderDetailVO> vo) {
		initAuthInfo();
		int result = 0;
		//등록 행이 있을때 발동
		if(vo.getCreatedRows().size()>0 || vo.getCreatedRows()!=null) {
			//반복문 돌려서 추가
			for (PurchaseOrderDetailVO item : vo.getCreatedRows()) {
				item.setComId(comId);
			    result += mapper.purchaseOrderDetailAdd(item);
			}	
		}
		//수정 행이 있을때 발동
		if(vo.getUpdatedRows().size()>0 ||  vo.getUpdatedRows()!=null) {
			//반복문 돌려서 수정
			for (PurchaseOrderDetailVO item : vo.getUpdatedRows()) {
				item.setComId(comId);
			    result += mapper.purOrderUpdateRows(item);
			}	
	    }
		//삭제 행이 있을때 발동
		if(vo.getDeleteRows().size()>0 || vo.getDeleteRows()!=null) {
			//반복문 돌려서 삭제
			for (PurchaseOrderDetailVO item : vo.getDeleteRows()) {
				item.setComId(comId);
			    result += mapper.purOrderDeleteRows(item);
			}
	    }
		
		return result;
	}
	//발주 삭제
	@Override
	@Transactional
	public int purDelete(String param) {
		initAuthInfo();
		int result = 0;
		//메인 삭제
		result = mapper.purMainDelete(param,comId);
		//상세 삭제
		result += mapper.purDTtlDelete(param, comId);
		
		return result;
	}
}
