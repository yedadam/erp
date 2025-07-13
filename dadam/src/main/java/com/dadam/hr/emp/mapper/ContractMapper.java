package com.dadam.hr.emp.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.dadam.hr.emp.service.ContractVO;

/**
 * 근로계약 매퍼 인터페이스
 * - 근로계약 관련 DB 연동 메서드 정의
 */
@Mapper
public interface ContractMapper {

    /**
     * 근로계약 목록 조회
     * @param param - 검색 파라미터
     * @return 근로계약 리스트
     */
    public List<ContractVO> findContractList(Map<String, Object> param);

    /**
     * 근로계약 상세 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 근로계약 정보
     */
    public ContractVO getContractDetail(@Param("empId") String empId, @Param("comId") String comId);

    /**
     * 근로계약 등록
     * @param contractVO - 근로계약 정보
     * @return 등록 결과
     */
    public int insertContract(ContractVO contractVO);

    /**
     * 근로계약 수정
     * @param contractVO - 근로계약 정보
     * @return 수정 결과
     */
    public int updateContract(ContractVO contractVO);

    /**
     * 근로계약 삭제
     * @param param - 사원번호, 회사ID
     * @return 삭제 결과
     */
    public int deleteContract(Map<String, String> param);

    /**
     * 최대 계약코드 조회
     * @return 계약코드
     */
    public String getMaxContractCode();
} 