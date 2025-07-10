package com.dadam.subscribe.mapper;

import java.util.List;
import java.util.Map;

import com.dadam.subscribe.service.ErpUsersVO;
import com.dadam.subscribe.service.SubsListVO;

public interface SubsManagerMapper {
	
	//유저 구독 정보 조회
	public List<ErpUsersVO> erpUserList(Map<String,Object> map);
	
	//구독 정보 상세 조회
	public List<SubsListVO> subsInfo(String cid);
	
	//매니저가 직접 구독정보 넣기
    public int subsManagerAdd(SubsListVO param);
    
    //매니저가 직접 정보 수정
    public int erpManagerUpdate(ErpUsersVO vo);
    
    
    //매니저가 직접 구독권 수정
    public int subsManagerUpdate(SubsListVO param);
    //구독권있는지 조회
    public String subsCheck(String param);
}
