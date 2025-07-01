package com.dadam.standard.vender.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dadam.standard.vender.service.VenderVO;

public interface VenderMapper {
	List<VenderVO> venderFindAll(@Param("comId") String comId,@Param("type") String type, @Param("value") String value); //거래처목록조회  
}
