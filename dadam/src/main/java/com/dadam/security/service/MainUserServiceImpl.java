package com.dadam.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dadam.security.mapper.UserMapper;

/**
 * @author 신현욱
 * @since 2025.06
 * @desc ERP 외부 회원가입 및 아이디 중복 체크 서비스 구현체
 * @history
 *   - 2025.06 : 최초 작성, ID 중복체크 및 회원가입 구현
 */
@Service
public class MainUserServiceImpl {

    // 사용자 관련 DB 작업 처리하는 Mapper
    @Autowired
    UserMapper mapper;

    // Spring Security 기반 비밀번호 암호화 처리용 빈
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @desc 아이디 중복 확인
     * @param comId 입력된 회사 ID
     * @return 중복 여부 (0: 사용 가능, 1 이상: 중복)
     */
    public int idCheck(String comId) {
        return mapper.checkId(comId);
    }

    /**
     * @desc 회원가입 처리 (비밀번호 암호화 포함)
     * @param vo 가입할 사용자 정보
     * @return DB insert 결과 (0 이상 성공)
     */
    public int insertId(ErpUserVO vo) {
        // 비밀번호 암호화 후 저장
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        return mapper.insertId(vo);
    }
}
