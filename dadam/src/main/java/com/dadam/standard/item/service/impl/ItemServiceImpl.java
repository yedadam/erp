package com.dadam.standard.item.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.standard.item.mapper.ItemMapper;
import com.dadam.standard.item.service.ItemService;
import com.dadam.standard.item.service.ItemVO;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	ItemMapper mapper;

	// 전체조회
	@Override
	public List<ItemVO> itemFindAll() {
		List<ItemVO> result = mapper.itemFindAll();
		return result;
	}
}
