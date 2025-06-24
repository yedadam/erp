package com.dadam.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.common.mapper.CodeMapper;
import com.dadam.common.service.CodeService;
import com.dadam.common.service.CodeVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	CodeMapper codeMapper;

	@Override
	public List<CodeVO> getCodeList(String mainCode) {
		List<CodeVO> result= codeMapper.selectCode(mainCode); 
		return result;
	}


	@Override
	public List<Map> getCodeMap(String mainCode) {
		 List<Map> results = new ArrayList<Map>();  
		for(CodeVO vo  :codeMapper.selectCode(mainCode)) {
			Map<String ,String> map=new HashMap<String ,String>();
			map.put("value", vo.getSubCode());
			map.put("text", vo.getSubName());  
			results.add(map); 
		}
		return results;
	}

}
