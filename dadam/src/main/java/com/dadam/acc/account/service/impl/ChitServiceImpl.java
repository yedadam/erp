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
	public List<ChitVO> chitSearch(Map<String, Object> params) {
	    String comId = this.comId;
	    params.put("comId", comId);
	    return chitMapper.chitSearch(params);
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
        if ("cht03".equals(ttype)) { // "수금"에 해당하는 거래유형 코드
            chitMapper.modifyVarBal(articleCode, totPrice, comId);
        }
	}
	
	@Override
	public List<ChitVO> getAutoChitRules(String comId) {
        return chitMapper.selectAutoChitRules(comId);
	}
	
	private String normalizeIndType(String raw) {
	    if (raw == null || raw.isBlank()) return null;
	    if (raw.startsWith("itp")) return raw;
	    if (raw.matches("^\\d{1,2}$")) return "itp" + String.format("%02d", Integer.parseInt(raw));
	    return chitMapper.findIndTypeCodeByName(raw);
	}

	private String normalizeChitType(String raw) {
	    if (raw == null || raw.isBlank()) return null;
	    if (raw.startsWith("cht")) return raw;
	    if (raw.matches("^\\d{1,2}$")) return "cht" + String.format("%02d", Integer.parseInt(raw));
	    return chitMapper.findTypeCodeByName(raw);
	}

	private String normalizeAcctCode(String raw) {
	    if (raw == null || raw.isBlank()) return null;
	    if (raw.matches("^\\d+$")) return raw; // 숫자형 코드면 그대로
	    return chitMapper.findAcctCodeByName(raw);
	}

	
	@Override
	public void saveAllRules(List<ChitVO> rules) {
	    initAuthInfo(); // comId 세팅

	    for (ChitVO rule : rules) {
	        if ("delete".equals(rule.getStatus())) {
	            if (rule.getRuleId() != null) {
	                chitMapper.deleteRule(rule.getRuleId());
	            }
	            continue;
	        }

	        rule.setComId(comId);

	        // ✅ 여기에 정규화 삽입!
	        String chitType = normalizeChitType(rule.getChitType());
	        String itpType = normalizeIndType(rule.getItpType());
	        String acctCode = normalizeAcctCode(rule.getAcctCode());

	        if (chitType == null) throw new IllegalArgumentException("거래유형 코드 없음");
	        if (itpType == null) throw new IllegalArgumentException("차/대변 코드 없음");
	        if (acctCode == null) throw new IllegalArgumentException("계정과목 코드 없음");

	        rule.setChitType(chitType);
	        rule.setItpType(itpType);
	        rule.setAcctCode(acctCode);

	        chitMapper.insertRule(rule);
	    }
	}



    @Override
    public List<Map<String, Object>> getAutoRules(String chitType, String comId) {
        return chitMapper.selectAutoRules(chitType, comId);
    }

    @Override
    public void saveAllRulesSeparated(List<ChitVO> createdRows, List<ChitVO> updatedRows, List<ChitVO> deletedRows) {
        initAuthInfo(); // comId 세팅
        // [2024-07-07] 자동분개 규칙 저장 로직 Account 방식으로 리팩토링
        // - createdRows: 신규, updatedRows: 수정, deletedRows: 삭제
        // - MERGE 대신 insertRule, updateRule, deleteRule만 사용
        // - ruleId가 없으면 getNextRuleId로 자동생성
        // - 각 단계별로 진단 로그 추가
        // 생성
        if (createdRows != null) {
            for (ChitVO rule : createdRows) {
                rule.setComId(comId);
                String chitType = normalizeChitType(rule.getChitType());
                String itpType = normalizeIndType(rule.getItpType());
                String acctCode = normalizeAcctCode(rule.getAcctCode());
                if (chitType == null) throw new IllegalArgumentException("거래유형 코드 없음");
                if (itpType == null) throw new IllegalArgumentException("차/대변 코드 없음");
                if (acctCode == null) throw new IllegalArgumentException("계정과목 코드 없음");
                rule.setChitType(chitType);
                rule.setItpType(itpType);
                rule.setAcctCode(acctCode);
                // [2024-07-07] ruleId 자동생성 및 진단 로그
                if (rule.getRuleId() == null || rule.getRuleId().isBlank()) {
                    String nextRuleId = chitMapper.getNextRuleId(comId);
                    System.out.println("[DEBUG] getNextRuleId 쿼리 결과: " + nextRuleId);
                    if (nextRuleId == null || nextRuleId.isBlank()) {
                        nextRuleId = "rule01";
                    }
                    System.out.println("[DEBUG] 최종 생성된 ruleId: " + nextRuleId);
                    rule.setRuleId(nextRuleId);
                }
                System.out.println("[DEBUG] insert 전 ruleId: " + rule.getRuleId());
                chitMapper.insertRule(rule);
            }
        }
        // 수정
        if (updatedRows != null) {
            for (ChitVO rule : updatedRows) {
                rule.setComId(comId);
                String chitType = normalizeChitType(rule.getChitType());
                String itpType = normalizeIndType(rule.getItpType());
                String acctCode = normalizeAcctCode(rule.getAcctCode());
                if (chitType == null) throw new IllegalArgumentException("거래유형 코드 없음");
                if (itpType == null) throw new IllegalArgumentException("차/대변 코드 없음");
                if (acctCode == null) throw new IllegalArgumentException("계정과목 코드 없음");
                rule.setChitType(chitType);
                rule.setItpType(itpType);
                rule.setAcctCode(acctCode);
                // [2024-07-07] ruleId가 없으면 신규로 간주하여 insert, 있으면 update
                if (rule.getRuleId() == null || rule.getRuleId().isBlank()) {
                    String nextRuleId = chitMapper.getNextRuleId(comId);
                    System.out.println("[DEBUG] getNextRuleId 쿼리 결과: " + nextRuleId);
                    if (nextRuleId == null || nextRuleId.isBlank()) {
                        nextRuleId = "rule01";
                    }
                    System.out.println("[DEBUG] 최종 생성된 ruleId: " + nextRuleId);
                    rule.setRuleId(nextRuleId);
                    System.out.println("[DEBUG] update->insert 전 ruleId: " + rule.getRuleId());
                    chitMapper.insertRule(rule);
                } else {
                    System.out.println("[DEBUG] update 전 ruleId: " + rule.getRuleId());
                    chitMapper.updateRule(rule);
                }
            }
        }
        // 삭제
        if (deletedRows != null) {
            for (ChitVO rule : deletedRows) {
                // [2024-07-07] ruleId가 있을 때만 삭제
                if (rule.getRuleId() != null && !rule.getRuleId().isBlank()) {
                    System.out.println("[DEBUG] delete 전 ruleId: " + rule.getRuleId());
                    chitMapper.deleteRule(rule.getRuleId());
                }
            }
        }
    }

}	
