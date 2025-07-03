package com.dadam.standard.vender.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dadam.standard.vender.service.VenderVO;

public interface VenderMapper {
	List<VenderVO> venderFindAll(@Param("comId") String comId,@Param("type") String type, @Param("value") String value); //거래처목록조회 
	int insertVender(VenderVO vender); //거래처등록
	int updateVender(VenderVO vender); //거래처정보수정 
	int deleteVender(@Param("comId")String comId,@Param("vdrCode") String vdrCode); //거래처삭제하기
}
