package com.dadam.subscribe.service;

import java.util.List;

public interface SubscribeService {

	public List<SubscribeVO> menuList();
	
	//등록
	public int subsAdd(SubsListVO param);
	
	//중복확인
	public int sameCheck(String param);
	
	
}
