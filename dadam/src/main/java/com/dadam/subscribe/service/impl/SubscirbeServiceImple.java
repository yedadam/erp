package com.dadam.subscribe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.subscribe.mapper.SubscribeMapper;
import com.dadam.subscribe.service.SubsListVO;
import com.dadam.subscribe.service.SubscribeService;
import com.dadam.subscribe.service.SubscribeVO;

@Service
public class SubscirbeServiceImple implements SubscribeService{
	@Autowired
	SubscribeMapper subsMapper;
	public List<SubscribeVO> menuList(){
		return subsMapper.menuList();
	};
	
	@Override
	public int subsAdd(SubsListVO param) {
		System.out.println("가가");
		System.out.println(param);
		//해당 값에 맞게 optionCode 변경
		if(param.getOptionCode()=="Lite") {
		 param.setOptionCode("oc-101");
		}else if (param.getOptionCode()=="Standard") {
		 param.setOptionCode("oc-102");
		}else if(param.getOptionCode()=="Premium") {
		 param.setOptionCode("oc-103");	
		}
		//등록
	    int result = subsMapper.subsAdd(param);
		return result;
	}
}
