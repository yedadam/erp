package com.dadam.standard.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.standard.item.service.ItemVO;

public interface ItemMapper {
	public List<ItemVO> itemFindAll();

}
