package com.dadam.acc.account.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.acc.account.mapper.ChitMapper;
import com.dadam.acc.account.service.ChitService;
import com.dadam.acc.account.service.ChitVO;
import com.dadam.security.service.LoginUserAuthority;

@Service
public class ChitServiceImpl implements ChitService{
	

    //comName 가져오기
    String comId = "com-101";
    public void initAuthInfo() {
        //로그인 객체값 연결
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //로그인 객체 가져오기
        Object principal = auth.getPrincipal();

        if (principal instanceof LoginUserAuthority) {
        	LoginUserAuthority user = (LoginUserAuthority) principal;
            comId = user.getComId();
            System.out.println("회사명: " + comId);
        }
    }

	
	
	@Autowired ChitMapper chitMapper;
    	
	@Override
	public List<ChitVO> chitFindAll() {
		return chitMapper.chitFindAll(comId);
	}
	//계정과목 자동조회
	@Override
    public List<Map<String, String>> acctCodeFindByCode(String keyword) {
        return chitMapper.acctCodeFindByCode(keyword, comId);
    }
	@Override
	public List<Map<String, String>> indTypeFindByCode(String keyword) {
		return chitMapper.indTypeFindByCode(keyword, comId);
	}

	@Override
	public List<Map<String, String>> chtTypeFindByCode(String keyword) {
		return chitMapper.chtTypeFindByCode(keyword, comId);
	}
	
	
	@Override
	@Transactional
	public void saveAll(ChitVO chit) {

	    System.out.println("👉 impl에 넘어온 데이터: " + chit);

	    List<ChitVO> createdRows = chit.getCreatedRows();
	    List<ChitVO> updatedRows = chit.getUpdatedRows();

	    // 1. 전표 코드 유효성 확인
	    if (createdRows == null || createdRows.isEmpty()) {
	        throw new IllegalStateException("분개 데이터가 존재하지 않습니다.");
	    }

	    String chitCode = createdRows.get(0).getChitCode(); // 가장 첫 행에서 전표코드 가져옴
	    String status = chitMapper.getStatusByChitCode(chitCode, comId);

	    if (!"cst01".equals(status)) {
	        throw new IllegalStateException("해당 전표는 이미 처리되었거나 유효하지 않습니다.");
	    }

	    // 2. 분개 행 등록
	    for (ChitVO row : createdRows) {
	        row.setChitCode(chitCode); // 누락될 수 있으므로 보장
	        row.setComId(comId);
	        chitMapper.insert(row);
	    }

	    // 3. 전표 상태 업데이트 (예: cst02 결제 처리)
	    ChitVO updateVO = new ChitVO();
	    updateVO.setChitCode(chitCode);
	    updateVO.setStatus("cst02");
	    updateVO.setComId(comId);
	    updateVO.setNote(chit.getNote()); // 메모 있을 경우 전달
	    chitMapper.update(updateVO);

	    // 4. 수정된 분개 행 처리 (있다면)
	    if (updatedRows != null && !updatedRows.isEmpty()) {
	        for (ChitVO row : updatedRows) {
	            row.setChitCode(chitCode); // 누락 방지
	            row.setComId(comId);
	            chitMapper.update(row);
	        }
	    }
	}
	@Override
	@Transactional
	public void modifyChitPay(String chitCode, String articleCode, int totPrice) {
        // 1. 전표 상태 변경
        chitMapper.modifySta(chitCode, comId);

        // 2. 여신잔액 변경
        String ttype = chitMapper.getTtypeByChitCode(chitCode, comId);  // 추가 필요
        if ("cht01".equals(ttype)) { // "수금"에 해당하는 거래유형 코드
            chitMapper.modifyVarBal(articleCode, totPrice, comId);
        }
	}
	
	@Override
	public List<ChitVO> getAutoChitRules(String comId) {
        return chitMapper.selectAutoChitRules(comId);
	}
	
    @Override
    public void saveAutoChitRules(List<ChitVO> rules) {
        for (ChitVO vo : rules) {
            chitMapper.mergeAutoChitRule(vo);
        }
    }
	
}	
