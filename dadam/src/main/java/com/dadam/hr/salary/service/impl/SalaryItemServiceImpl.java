package com.dadam.hr.salary.service.impl;

import com.dadam.hr.salary.mapper.SalaryItemMapper;
import com.dadam.hr.salary.service.SalaryItemService;
import com.dadam.hr.salary.service.SalaryItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 급여항목 마스터 Service 구현체
 */
@Service
public class SalaryItemServiceImpl implements SalaryItemService {
    @Autowired
    private SalaryItemMapper salaryItemMapper;

    @Override
    public List<SalaryItemVO> getSalaryItemList(String comId) {
        return salaryItemMapper.selectSalaryItemList(comId);
    }
    @Override
    public SalaryItemVO getSalaryItem(String comId, String allowCode) {
        return salaryItemMapper.selectSalaryItem(comId, allowCode);
    }
    @Override
    public int addSalaryItem(SalaryItemVO vo) {
        return salaryItemMapper.insertSalaryItem(vo);
    }
    @Override
    public int modifySalaryItem(SalaryItemVO vo) {
        return salaryItemMapper.updateSalaryItem(vo);
    }
    @Override
    public int removeSalaryItem(String comId, String allowCode) {
        return salaryItemMapper.deleteSalaryItem(comId, allowCode);
    }
} 