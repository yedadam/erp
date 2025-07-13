package com.dadam.main.mapper;

import java.util.List;

import com.dadam.main.service.MainVO;

public interface MainMapper {

	public int todayOrderPrice(String comId);
	public int todayPurchasePrice(String comId);
	public int todayPurchaseQunatity(String comId);
	public int todayTranPrice(String comId);
	
	public List<MainVO> lineChart(String comId);
	public List<MainVO> pieChart(String comId);
	public List<MainVO> safeList(String comId);
	public List<MainVO> barChart(String comId);
}
