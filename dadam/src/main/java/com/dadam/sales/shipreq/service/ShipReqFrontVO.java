package com.dadam.sales.shipreq.service;

import java.util.List;

import lombok.Data;

@Data
public class ShipReqFrontVO {
	ShipReqVO head;  //{}
	List<ShipReqDtlVO> dtl; //[{},{},{}]
}
