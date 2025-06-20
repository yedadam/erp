package com.dadam.subscribe.mapper;

import java.util.List;

import com.dadam.subscribe.service.SubsListVO;
import com.dadam.subscribe.service.SubscribeVO;

public interface SubscribeMapper {

	public List<SubscribeVO> menuList();
	
	//등록
	public int subsAdd(SubsListVO param);
}
