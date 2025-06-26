package com.dadam.sales.purchase.service;

import java.util.List;

import lombok.Data;

@Data
public class PurReqVO {
	private PurchaseOrderVO pur;
	private List<PurchaseOrderDetailVO> dtl;
}
