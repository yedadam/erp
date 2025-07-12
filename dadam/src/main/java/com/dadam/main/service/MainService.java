package com.dadam.main.service;

import java.util.List;

public interface MainService {
	public List<Integer> today(); 
	
	public List<MainVO> lineChart();
	
	public List<MainVO> pieChart();
	
	public List<MainVO> safeList();
	
	public List<MainVO> barChart();
}
