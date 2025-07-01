package com.dadam.sales.shipreq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dadam.sales.shipreq.service.ShipReqDtlVO;
import com.dadam.sales.shipreq.service.ShipReqVO;

public interface ShipreqMapper {
	public List<ShipReqVO> findShipreqList(@Param("type") String type, @Param("value") String value, @Param("comId") String comId); //출하의뢰 리스트조회
	public List<ShipReqDtlVO> findShipreqDtlList(String shipReqCode); //출하의뢰코드로 상세조회
	public int insertShipreqHead(ShipReqVO head); //헤더등록 
	public int insertShipreqDtl(ShipReqDtlVO dtl); //디테일 등록 
	public int updateStatusByordNo(String ordCode); //헤더등록시 상태값이 변경됨 
	public int updateShiPExpDate(ShipReqVO head);  //납기일자 변경하기 
	public int updateOrdStatus(String ordCode); //주문상태 바꾸기 
	public int deleteShipreqDtl(String shipReqCode ); //출하헤더 삭제시출하디테일 삭제하기
	public int deleteShipreqHead(String shipReqCode);//출하헤더 삭제하기
	public int deleteShipReqDtlBydtlno(String shipReqDtlCode); // 
}
