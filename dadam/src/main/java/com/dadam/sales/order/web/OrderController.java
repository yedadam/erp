package com.dadam.sales.order.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dadam.sales.order.service.OrdDtlVO;
import com.dadam.sales.order.service.OrderService;
import com.dadam.sales.order.service.OrdersVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
@RequestMapping("/erp/sales")
public class OrderController {
	
	final OrderService orderService;
	
	@GetMapping("/order")
	public String orderList() {
		
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
	
	@PostMapping("/ord/register")
	public String registerOrder(@RequestBody  Map<String, Object> data) {  
		System.out.println(data.get("header"));
		System.out.println(data.get("details"));
		
		  Map<String, Object> header = (Map<String, Object>) data.get("header");
		   // Jackson의 ObjectMapper 사용해서 OrdersVO로 변환
		  ObjectMapper mapper = new ObjectMapper();
		  OrdersVO order = mapper.convertValue(header, OrdersVO.class);
		 
		  
		  
		  System.out.println(order.getOrdCode());
		
		
	    return "ok";
	}
	@GetMapping("/test")
	public String test() {
		return "sales/test";
	}
	
	
}
