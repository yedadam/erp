package com.dadam.sales.shipreq.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.sales.shipreq.service.ShipReqDtlVO;
import com.dadam.sales.shipreq.service.ShipReqFrontVO;
import com.dadam.sales.shipreq.service.ShipReqVO;
import com.dadam.sales.shipreq.service.ShipreqService;



@RestController
@RequestMapping("/erp/sales")
public class ShipreqRestController {
	
	@Autowired
	ShipreqService service;
    	  
	//조회
	@GetMapping("/shipReqList")
	public List<ShipReqVO> shipReqList() {
		List<ShipReqVO> result = service.findShipreqList();
		System.out.println("조회");
		return result;
	}
	
	@GetMapping("/shipReqDtlList")
	public List<ShipReqDtlVO> shipReqDtlList(@RequestParam(name="shipReqCode")String shipReqCode){
		List<ShipReqDtlVO> result = service.findShipreqDtlList(shipReqCode);
		return result;
	}
	
	@PostMapping("/shipReqAdd")
	public int shipReqreg(@RequestBody ShipReqFrontVO req) {
		//등록메서드 집어넣기
		//req.getHead()
		System.out.println(req);
		service.insertShipreqReg(req);		
		return 0;
	}
	
	//출하 메인 수정
	@PutMapping("/shipReqMainModify")
	public int shipReqMainModify(@RequestBody ShipReqVO param) {
		System.out.println(param);
		int result = service.updateShiPExpDate(param);
		return result;
	}
	
	//발주 의뢰 조회
//	@GetMapping("/purRequestList")
//	public List<PurchaseOrderVO> purRequestList(@RequestParam(name = "param", required = false) PurchaseOrderVO param) {
//
//		List<PurchaseOrderVO> result = service.requestList();
//
//		return result;
//	}
//	
	//발주서 의뢰 상세 조회
//	@GetMapping("/purRqDetailList")
//	public List<PurchaseOrderVO> purRequestDtList(@RequestParam String param){
//		List<PurchaseOrderVO> result = service.requestDeatilList(param);
//		System.out.println(result);
//		return result;
//	}
	
	//발주서 등록
//	@PostMapping("/purAdd")
//	public int postMethodName(@RequestBody PurReqVO req) {
//		System.out.println("메인");
//		System.out.println(req);
//		int result  = service.purchaseOrderAdd(req);
//		
//		return result;
//		
//	}
	

	//발주서 디테일 수정
//	@PutMapping("/purDetailModify")
//	public int purDetailModify(@RequestBody GridData<PurchaseOrderDetailVO> vo) {
//		int result = service.purOderDtUpdate(vo);
//		return result;
//	}
	
}
