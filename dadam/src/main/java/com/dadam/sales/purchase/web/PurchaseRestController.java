package com.dadam.sales.purchase.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.common.service.GridData;
import com.dadam.sales.purchase.service.PurReqVO;
import com.dadam.sales.purchase.service.PurchaseOrderDetailVO;
import com.dadam.sales.purchase.service.PurchaseOrderVO;
import com.dadam.sales.purchase.service.PurchaseService;
import com.dadam.standard.vender.service.VenderVO;



@RestController
@RequestMapping("/erp/sales")
public class PurchaseRestController {
	
	@Autowired
	PurchaseService service;
    	  
	//조회
	@GetMapping("/purMainList")
	public List<PurchaseOrderVO> purchaseList(@RequestParam Map<String,Object> map) {
		
		List<PurchaseOrderVO> result = service.findPurchaseList(map);
		
		return result;
	}
	//발주 상세조회
	@GetMapping("/purDetailList/{purCode}")
	public List<PurchaseOrderDetailVO> purchaseDetailList(@PathVariable String purCode){
		List<PurchaseOrderDetailVO> result = service.findPurListByOrdNo(purCode);
		return result;
	}
	//발주 의뢰 조회
	@GetMapping("/purRequestList")
	public List<PurchaseOrderVO> purRequestList(@RequestParam(name = "type", required = false) String type,@RequestParam(name = "value", required = false) String value) {
		List<PurchaseOrderVO> result = service.requestList(type,value);
		return result;
	}
	
	//발주서 의뢰 상세 조회
	@GetMapping("/purRqDetailList")
	public List<PurchaseOrderVO> purRequestDtList(@RequestParam String param){
		List<PurchaseOrderVO> result = service.requestDeatilList(param);
		System.out.println(result);
		return result;
	}
	
	//거래처 조회  
	@GetMapping("/vederList")
	public List<VenderVO> venderList(@RequestParam (required = false) String type,@RequestParam (required = false) String value){
		List<VenderVO> result = service.venderList(type,value);
		return result;
	}
	
	//발주서 등록
	@PostMapping("/purAdd")
	public int postMethodName(@RequestBody PurReqVO req) {
		System.out.println("메인");
		System.out.println(req);
		int result  = service.purchaseOrderAdd(req);
		
		return result;
		
	}
	
	//발주서 메인 수정
	@PutMapping("/purMainModify")
	public int purMainModify(@RequestBody PurchaseOrderVO param) {
		System.out.println(param);
		int result = service.purOrderUpdate(param);
		return result;
	}
	//발주서 디테일 수정
	@PutMapping("/purDetailModify")
	public int purDetailModify(@RequestBody GridData<PurchaseOrderDetailVO> vo) {
		int result = service.purOderDtUpdate(vo);
		return result;
	}
	//발주 삭제
	@DeleteMapping("/purDelete/{param}")
	public int purDelete(@PathVariable String param) {
		int result = service.purDelete(param);
		return result;
	}
	//자동완성기능
	@GetMapping("/venderAuto")
	public List<VenderVO> venderAuto(@RequestParam String type, @RequestParam String value){
		List<VenderVO> result = service.autoVender(type,value);
		return result;
	} 
	
	
}
