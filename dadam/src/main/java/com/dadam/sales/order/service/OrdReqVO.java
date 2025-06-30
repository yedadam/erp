package com.dadam.sales.order.service;

import com.dadam.common.service.GridData;

import lombok.Data;

@Data
public class OrdReqVO {	
	OrdersVO ord; 
	GridData<OrdDtlVO> dtl;  
	
	
	
	
	
}
