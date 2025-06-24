package com.dadam.hr.emp.web;

import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.DeptService;
import com.dadam.hr.emp.service.EmpVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(controllers = EmpController.class, excludeAutoConfiguration = {
    org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
    org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
    org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
    org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration.class,
    org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration.class
})
@AutoConfigureMockMvc(addFilters = false)
class EmpControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpService empService;
    @MockBean
    private DeptService deptService;

    @Test
    @DisplayName("사원 목록 조회 API가 정상적으로 동작한다")
    void 사원_목록_조회_API_정상() throws Exception {
        EmpVO emp = new EmpVO();
        emp.setEmpId("e1001");
        when(empService.findEmpList(any(), any(), any())).thenReturn(List.of(emp));

        mockMvc.perform(get("/erp/hr/empList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].empId").value("e1001"));
    }

    @Test
    @DisplayName("사원 상세 조회 API가 정상적으로 동작한다")
    void 사원_상세_조회_API_정상() throws Exception {
        EmpVO emp = new EmpVO();
        emp.setEmpId("e1002");
        when(empService.findEmpDetail("e1002")).thenReturn(emp);

        mockMvc.perform(get("/erp/hr/empDetail").param("empId", "e1002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empId").value("e1002"));
    }

    @Test
    @DisplayName("사원 등록 API가 정상적으로 동작한다")
    void 사원_등록_API_정상() throws Exception {
        when(empService.insertEmp(any(EmpVO.class))).thenReturn(1);

        mockMvc.perform(post("/erp/hr/insertEmp")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("empName", "홍길동"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }
} 