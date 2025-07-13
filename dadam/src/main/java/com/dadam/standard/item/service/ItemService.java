package com.dadam.standard.item.service;

import java.util.List;

import org.springframework.stereotype.Service;

public interface ItemService {

	public List<ItemVO> itemFindAll(String type,String value);
	public List<ItemVO> itemFindit02(String type,String value);
}
