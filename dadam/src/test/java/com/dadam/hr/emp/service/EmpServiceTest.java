package com.dadam.hr.emp.service;

import com.dadam.hr.emp.mapper.EmpMapper;
import com.dadam.hr.emp.service.impl.EmpServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpServiceTest {
    @Mock
    private EmpMapper empMapper;
    @InjectMocks
    private EmpServiceImpl empService;

    @Test
    @DisplayName("사원 목록 조회가 정상적으로 동작한다")
    void 사원_목록_조회_정상() {
        // given
        EmpVO emp = new EmpVO();
        emp.setEmpId("e1001");
        when(empMapper.findEmpList(any(Map.class))).thenReturn(List.of(emp));

        // when
        List<EmpVO> result = empService.findEmpList(null, null, null);

        // then
        assertEquals(1, result.size());
        assertEquals("e1001", result.get(0).getEmpId());
    }

    @Test
    @DisplayName("사원 상세 조회가 정상적으로 동작한다")
    void 사원_상세_조회_정상() {
        // given
        EmpVO emp = new EmpVO();
        emp.setEmpId("e1003");
        when(empMapper.findEmpDetail("e1003")).thenReturn(emp);

        // when
        EmpVO result = empService.findEmpDetail("e1003");

        // then
        assertNotNull(result);
        assertEquals("e1003", result.getEmpId());
    }
} 