package com.dadam.sample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.sample.mapper.SampleMapper;
import com.dadam.sample.service.SampleService;
import com.dadam.sample.service.SampleVO;

//service bean등록
@Service
public class SampleServiceImple implements SampleService{
	
	//객체 주입
	@Autowired
	SampleMapper sampleMapper;
	
	//전체 조회
	@Override
	public List<SampleVO> findAll() {
		List<SampleVO> result = sampleMapper.findAll();
		return result;
	}
}
