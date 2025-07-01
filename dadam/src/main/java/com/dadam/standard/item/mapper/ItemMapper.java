package com.dadam.standard.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dadam.standard.item.service.ItemVO;

public interface ItemMapper {
	public List<ItemVO> itemFindAll(@Param("type") String type, @Param("value") String value, @Param("comId") String comId);

}
