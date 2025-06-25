package com.dadam.sales.order.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dadam.common.service.CodeService;
import com.dadam.common.service.CodeVO;
import com.dadam.sales.order.service.OrdDtlVO;
import com.dadam.sales.order.service.OrdReqVO;
import com.dadam.sales.order.service.OrderService;
import com.dadam.sales.order.service.OrdersVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
@RequestMapping("/erp/sales")
public class OrderController {
	
	final OrderService orderService;
	final CodeService codeService; 
	
	@GetMapping("/order")
	public String order(Model model) {
		
		model.addAttribute("payMethod",codeService.getCodeMap("opm"));
		model.addAttribute("status",codeService.getCodeMap("ost")); 

		return "sales/order";
	}
	
	@GetMapping("/orderList")
	@ResponseBody
	public List<OrdersVO> orderList(Model model) {
	//	model.addAttribute("ordList",orderservice.findOrderList()); 
		List<OrdersVO> result = orderService.findOrderList();
		return result;
	}
	@GetMapping("/ordDtlList")
	@ResponseBody
	public List<OrdDtlVO> orderDtlList(@RequestParam(name="ordCode")String ordCode ){
		List<OrdDtlVO> result=orderService.findOrdListByOrdNo(ordCode);
		return result;
	}
	@ResponseBody
	@PostMapping("/ord/register")
	public String registerOrder(@RequestBody OrdReqVO req) {
		req.getOrd().setEmpId("emp-101");
		String vdrcode=req.getOrd().getVdrCode();
		Long totPrice=req.getOrd().getTotPrice(); // 총금액 해당 거래처코드에 가서 credit_bal_price-totPrice 여신잔액  
		orderService.orderInsert(req); //insert 처리 		{ord:{}}
	    return "ok";
	}
	@ResponseBody
	@PutMapping("/ord/update")
	public String updateOrder(@RequestBody OrdersVO ord) {
		ord.setComId("com-101");
		//ord.setUpdateId("")
		orderService.updOrder(ord); 
		return "updok"; 
	}
	
	@ResponseBody
	@PutMapping("/ord/updDtl")
	public String updDtlOrd(@RequestBody OrdDtlVO dtl) {
		orderService.updOrdDtl(dtl); 
		System.out.println(dtl);
		System.out.println("ssssddddssss");
		return "ok"; 
	}
	@ResponseBody
	@DeleteMapping("/ord/delete")
	public String deleteOrder(@RequestParam(name="ordCode") String ordCode) {
		orderService.removeOrders(ordCode); 
		return "deleteok"; 
	}
	@ResponseBody
	@DeleteMapping("/ord/delOrdDtl")
	public String deleteOrdDtl(@RequestParam(name="ordDtlCode") String ordDtlCode) {
		orderService.deleteOrdDtl(ordDtlCode); 
		System.out.println("ddddd");
		return "OrdDtlDeleteOk"; 
	}	
	@GetMapping("/test")
	public String test() {
		return "sales/test";
	}
}
