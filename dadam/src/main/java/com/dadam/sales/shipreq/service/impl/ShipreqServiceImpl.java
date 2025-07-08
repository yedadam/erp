package com.dadam.sales.shipreq.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.sales.shipreq.mapper.ShipreqMapper;
import com.dadam.sales.shipreq.service.ShipReqDtlVO;
import com.dadam.sales.shipreq.service.ShipReqFrontVO;
import com.dadam.sales.shipreq.service.ShipReqVO;
import com.dadam.sales.shipreq.service.ShipreqService;
import com.dadam.security.service.LoginUserAuthority;
@Service
@Transactional
public class ShipreqServiceImpl implements ShipreqService {
	
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
	ShipreqMapper shipreqMapper;

	@Override
	public List<ShipReqVO> findShipreqList(Map<String,Object> map) {
		initAuthInfo();
		map.put("comId", comId);
		List<ShipReqVO> result=shipreqMapper.findShipreqList(map); 
		return result;
	}

	@Override
	public List<ShipReqDtlVO> findShipreqDtlList(String shipReqCode) {
		initAuthInfo(); 
		List<ShipReqDtlVO> result=shipreqMapper.findShipreqDtlList(shipReqCode,comId); 
		return result;
	}

	@Override
	public int insertShipreqReg(ShipReqFrontVO req) {
	
		initAuthInfo();
		
		String ordCode=req.getHead().getOrdCode(); 
		req.getHead().setComId(comId); //comId 설정   
		
		shipreqMapper.insertShipreqHead(req.getHead()); //헤더먼저등록
		shipreqMapper.updateStatusByordNo(ordCode,comId); 	// 출고대기 ost02로 변경 
		String shipreqCode=shipreqMapper.findMaxShipReqNo(comId);
		
		for(int i=0;i<req.getDtl().size(); i++ ) {
		  req.getDtl().get(i).setShipReqCode(shipreqCode); //shipreqCode로 지정		
		  req.getDtl().get(i).setComId(comId);
		  shipreqMapper.insertShipreqDtl(req.getDtl().get(i)); //디테일 for문 돌리면서 등록 
		}
		return 0;
	}
	@Override
	public int updateShiPExpDate(List<ShipReqVO> list) {
		initAuthInfo(); 
		for(int i=0;i<list.size();i++) {
			list.get(i).setComId(comId);
			shipreqMapper.updateShiPExpDate(list.get(i)); 
		}
		
		return 0;
	}

	@Override
	public int deleteShipReq(ShipReqFrontVO req) {
		initAuthInfo();
		req.getHead().setComId(comId); //comId 부여 
		shipreqMapper.updateOrdStatus(req.getHead().getOrdCode(),req.getHead().getComId()); 
		System.out.println("updateOrdStatus완료");
		shipreqMapper.deleteShipreqDtl(req.getHead().getShipReqCode(),comId);
		System.out.println("deleteShipreqDtl완료");
		shipreqMapper.deleteShipreqHead(req.getHead().getShipReqCode(),comId);
		return 0;
	}
	//dtl번호받아서 삭제하기 
	@Override
	public int deleteShipReqDtlBydtlno(ShipReqFrontVO req) {
		initAuthInfo(); 
		req.getHead().setComId(comId);
		String shipReqCode=req.getHead().getShipReqCode(); //출하의뢰번호 
		for(int i=0; i<req.getDtl().size();i++) {
		   req.getDtl().get(i).setComId(comId);
		   shipreqMapper.updateTotPriceAfterDelete(shipReqCode,req.getDtl().get(i).getShipReqDtlCode(), comId); //출하의뢰 헤더번호 
		   shipreqMapper.deleteShipReqDtlBydtlno(req.getDtl().get(i).getShipReqDtlCode(),comId); 
		}
		return 0;
	}

	 
}
