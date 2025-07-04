package com.dadam.standard.vender.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dadam.standard.vender.service.VenderVO;

public interface VenderMapper {
	List<VenderVO> venderFindAll(@Param("comId") String comId,@Param("type") String type, @Param("value") String value); //거래처목록조회 
	List<VenderVO> venderFindAllList(@Param("comId") String comId,@Param("type") String type, @Param("value") String value);
	int insertVender(VenderVO vender); //거래처등록
	int updateVender(VenderVO vender); //거래처정보수정 
	int deleteVender(@Param("comId")String comId,@Param("vdrCode") String vdrCode); //거래처삭제하기
	String findVenderMaxno(@Param("comId")String comId); //거래처번호 최대값 
	
}
