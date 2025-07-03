package com.dadam.hr.attendance.service;

import com.dadam.hr.emp.mapper.EmpMapper;
import com.dadam.hr.emp.service.EmpVO;
import com.dadam.hr.attendance.service.AnnualLeaveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

/**
 * 연차 자동 갱신 스케줄러
 */
@Component
public class AnnualLeaveScheduler {

    /** 사원 Mapper */
    @Autowired
    private EmpMapper empMapper;

    /**
     * 매년 1월 1일 0시 전체 사원 연차 자동 갱신
     */
    @Scheduled(cron = "0 0 0 1 1 *")
    public void updateAnnualLeaveForAllEmployees() {
        List<EmpVO> employees = empMapper.findEmpList(null); // 전체 사원 조회 (파라미터는 필요에 따라 수정)
        for (EmpVO emp : employees) {
            int totalLeave = AnnualLeaveUtil.calculateAnnualLeaveTotal(emp.getHireDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
            emp.setAnnualLeaveTotal(totalLeave);
            empMapper.updateEmp(emp); // 연차 총일수 업데이트 (updateEmp 메서드 활용)
        }
        System.out.println("[연차 자동갱신] 전체 사원 연차 갱신 완료: " + LocalDate.now());
    }
} 