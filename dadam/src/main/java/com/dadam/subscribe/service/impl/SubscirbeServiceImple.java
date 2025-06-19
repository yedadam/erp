package com.dadam.subscribe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.subscribe.mapper.SubscribeMapper;
import com.dadam.subscribe.service.SubscribeService;
import com.dadam.subscribe.service.SubscribeVO;

@Service
public class SubscirbeServiceImple implements SubscribeService{
	@Autowired
	SubscribeMapper subsMapper;
	public List<SubscribeVO> menuList(){
		return subsMapper.menuList();
	};
}
