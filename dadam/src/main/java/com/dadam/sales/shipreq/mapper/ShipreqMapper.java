package com.dadam.sales.shipreq.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dadam.sales.shipreq.service.ShipReqDtlVO;
import com.dadam.sales.shipreq.service.ShipReqVO;

public interface ShipreqMapper {
	public List<ShipReqVO> findShipreqList(Map<String,Object> map); //출하의뢰 리스트조회
	public List<ShipReqDtlVO> findShipreqDtlList(@Param("shipReqCode")String shipReqCode,@Param("comId") String comId); //출하의뢰코드로 상세조회
	public int insertShipreqHead(ShipReqVO head); //헤더등록 
	public int insertShipreqDtl(ShipReqDtlVO dtl); //디테일 등록 
	public int updateStatusByordNo(@Param("ordCode")String ordCode,@Param("comId")String comId); //헤더등록시 상태값이 변경됨 
	public int updateShiPExpDate(ShipReqVO head);  //납기일자 변경하기 
	public int updateOrdStatus(@Param("ordCode")String ordCode,@Param("comId")String comId); //주문상태 바꾸기 
	public int deleteShipreqDtl(@Param("shipReqCode")String shipReqCode,@Param("comId")String comId ); //출하헤더 삭제시출하디테일 삭제하기
	public int deleteShipreqHead(@Param("shipReqCode")String shipReqCode,@Param("comId")String comId);//출하헤더 삭제하기
	public int deleteShipReqDtlBydtlno(@Param("shipReqDtlCode") String shipReqDtlCode,@Param("comId")String comId); // 
	public String findMaxShipReqNo(String comId); //출하의뢰 max 넘버찾기
	
	int updateTotPriceAfterDelete(@Param("shipReqCode")String shipReqCode,  @Param("shipReqDtlCode")  String shipReqDtlCode,@Param("comId")String comId); //행삭제후 헤더 총가격 변경 
}
