package com.dadam.standard.vender.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.standard.vender.mapper.VenderMapper;
import com.dadam.standard.vender.service.VenderService;
import com.dadam.standard.vender.service.VenderVO;
@Service
public class VenderServiceImpl implements VenderService {
	@Autowired
	VenderMapper mapper; 
	
	@Override
	public List<VenderVO> venderFindAll() {
		List<VenderVO> result=mapper.venderFindAll(); 
		return result;
	}

}
