package com.dadam.inventory.outbound.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.inventory.outbound.service.OutboundVO;

@Mapper
public interface OutboundMapper {

	public List<OutboundVO> selectShipRequestOutBound(OutboundVO vo);
}
