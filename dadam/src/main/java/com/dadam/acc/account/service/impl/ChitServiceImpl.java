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
	@Transactional
	public void saveAll(ChitVO chit) {

	    System.out.println("👉 impl에 넘어온 데이터: " + chit);

	    List<ChitVO> createdRows = chit.getCreatedRows();
	    List<ChitVO> updatedRows = chit.getUpdatedRows();

	    // ✅ 1. 전표 코드 유효성 확인
	    if (createdRows == null || createdRows.isEmpty()) {
	        throw new IllegalStateException("분개 데이터가 존재하지 않습니다.");
	    }

	    String chitCode = createdRows.get(0).getChitCode(); // 가장 첫 행에서 전표코드 가져옴
	    String status = chitMapper.getStatusByChitCode(chitCode);

	    if (!"cst01".equals(status)) {
	        throw new IllegalStateException("해당 전표는 이미 처리되었거나 유효하지 않습니다.");
	    }

	    // ✅ 2. 분개 행 등록
	    for (ChitVO row : createdRows) {
	        row.setChitCode(chitCode); // 누락될 수 있으므로 보장
	        chitMapper.insert(row);
	    }

	    // ✅ 3. 전표 상태 업데이트 (예: cst02 결제 처리)
	    ChitVO updateVO = new ChitVO();
	    updateVO.setChitCode(chitCode);
	    updateVO.setStatus("cst02");
	    updateVO.setNote(chit.getNote()); // 메모 있을 경우 전달
	    chitMapper.update(updateVO);

	    // ✅ 4. 수정된 분개 행 처리 (있다면)
	    if (updatedRows != null && !updatedRows.isEmpty()) {
	        for (ChitVO row : updatedRows) {
	            row.setChitCode(chitCode); // 누락 방지
	            chitMapper.update(row);
	        }
	    }
	}

	
	



}	
