package com.dadam.sales.shipreq.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public List<ShipReqVO> shipReqList(@RequestParam  Map<String,Object> map){
		List<ShipReqVO> result = service.findShipreqList(map);
		System.out.println("map=>>>"+map);
		
		System.out.println(result);
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
	public int shipReqMainModify(@RequestBody List<ShipReqVO> list ) {
		int result = service.updateShiPExpDate(list);
		return result;
	}
	
	//삭제하기 헤더 
	@DeleteMapping("/shipReqDel")
	public int shipReqDel(@RequestBody ShipReqFrontVO req) {
		service.deleteShipReq(req);  //주문상태 ost01-> 디테일삭제 -> 헤더삭제 
		return 0; 
	}
	@DeleteMapping("/shipReqDelDtl")
	public int shipReqDelDtl(@RequestBody ShipReqFrontVO req) {
		service.deleteShipReqDtlBydtlno(req); 
		return 0; 
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
