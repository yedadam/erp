package com.dadam.subscribe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.subscribe.mapper.SubsManagerMapper;
import com.dadam.subscribe.service.ErpUsersVO;
import com.dadam.subscribe.service.SubsListVO;
import com.dadam.subscribe.service.SubsManagerService;

/**
 * @author 신현욱
 * @since 2025.06.21
 * @desc 구독 정보 관리하는 서비스 구현체임
 * @history
 *   - 2025.06.24 신현욱 : 최초 작성, ERP 유저랑 구독정보 조회기능 구현함
 *   - 2025.06.25 신현욱 : 구독 정보 추가랑 수정 기능도 넣음
 */
@Service
public class SubsManagerServiceImpl implements SubsManagerService {
	
	@Autowired
	SubsManagerMapper managerMapper;

	/**
	 * @desc ERP 유저 목록 전체 조회
	 * @return List<ErpUsersVO> 구독 대상 ERP 유저 리스트
	 */
	@Override
	public List<ErpUsersVO> erpUserList(Map<String,Object> map) {
        List<ErpUsersVO> result = managerMapper.erpUserList(map);	  
		return result;
	}
	
	/**
	 * @desc 특정 회사(comId)의 구독 상세 내역 조회
	 * @param comId 회사 ID
	 * @return List<SubsListVO> 구독 상세 내역 리스트
	 */
	@Override
	public List<SubsListVO> subsList(String comId) {
	   List<SubsListVO> result = managerMapper.subsInfo(comId);
		return result;
	}

	/**
	 * @desc 구독 정보 등록 또는 수정 + ERP 유저 정보도 같이 수정함
	 * @param vo 수정할 유저 정보 및 구독 정보가 담긴 VO
	 * @return int 처리된 행 수, 0 이상이면 성공
	 * @implNote subsCheck 결과에 따라 구독 정보 insert or update 수행
	 */
	@Transactional
	@Override
	public int managerUpdate(ErpUsersVO vo) {
		String check = managerMapper.subsCheck(vo.getComId());
		System.out.println(check); 

		int result = 0;
		
		vo.getSubsList().get(0).setSubsCode(check);
		vo.getSubsList().get(0).setComId(vo.getComId());

		//기존 구독 있으면 update 없으면 insert
		if (check == null) {
			result = managerMapper.subsManagerAdd(vo.getSubsList().get(0));
		} else {
			result = managerMapper.subsManagerUpdate(vo.getSubsList().get(0));
		}

		//유저도 updqte
		result = managerMapper.erpManagerUpdate(vo);

		return result;
	}
}
