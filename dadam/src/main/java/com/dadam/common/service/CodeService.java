package com.dadam.common.service;

import java.util.List;
import java.util.Map;


public interface CodeService  {
	public List<CodeVO> getCodeList(String mainCode); 
	public List<Map> getCodeMap(String mainCode); 
	
}
