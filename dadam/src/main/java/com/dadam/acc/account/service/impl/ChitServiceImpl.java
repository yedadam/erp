package com.dadam.acc.account.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.acc.account.mapper.ChitMapper;
import com.dadam.acc.account.service.ChitService;
import com.dadam.acc.account.service.ChitVO;

@Service
public class ChitServiceImpl implements ChitService{
	
	@Autowired ChitMapper chitMapper;
	
	@Override
	public List<ChitVO> chitFindAll() {
		return chitMapper.chitFindAll();
	}
	//계정과목 자동조회
	@Override
    public List<Map<String, String>> acctCodeFindByCode(String keyword) {
        return chitMapper.acctCodeFindByCode(keyword);
    }
	@Override
	public List<Map<String, String>> indTypeFindByCode(String keyword) {
		return chitMapper.indTypeFindByCode(keyword);
	}

	@Override
	public List<Map<String, String>> chitTypeFindByCode(String keyword) {
		return chitMapper.chitTypeFindByCode(keyword);
	}
	
	
	@Override
	public void saveAll(ChitVO chit) {
		

	    System.out.println("👉 impl에 넘어온 데이터: " + chit);

	    if (chit.getCreatedRows() != null) {
	        for (ChitVO row : chit.getCreatedRows()) {
	            chitMapper.insert(row);
	        }
	    }

	    if (chit.getUpdatedRows() != null) {
	        for (ChitVO row : chit.getUpdatedRows()) {
	            chitMapper.update(row);
	        }
	    }

//	    if (chit.getDeletedRows() != null) {
//	        for (ChitVO row : chit.getDeletedRows()) {
//	            chitMapper.delete(row);
//	        }
//	    }
	}
	



}	
