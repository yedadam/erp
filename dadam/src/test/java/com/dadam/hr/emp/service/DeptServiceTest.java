package com.dadam.hr.emp.service;

import com.dadam.hr.emp.mapper.DeptMapper;
import com.dadam.hr.emp.service.impl.DeptServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeptServiceTest {
    @Mock
    private DeptMapper deptMapper;
    @InjectMocks
    private DeptServiceImpl deptService;

    @Test
    @DisplayName("부서 목록 조회가 정상적으로 동작한다")
    void 부서_목록_조회_정상() {
        // given
        DeptVO dept = new DeptVO();
        dept.setDeptCode("D001");
        dept.setDeptName("인사팀");
        when(deptMapper.findAllDepartments()).thenReturn(List.of(dept));

        // when
        List<DeptVO> result = deptService.findAllDepartments();

        // then
        assertEquals(1, result.size());
        assertEquals("D001", result.get(0).getDeptCode());
        assertEquals("인사팀", result.get(0).getDeptName());
    }
} 