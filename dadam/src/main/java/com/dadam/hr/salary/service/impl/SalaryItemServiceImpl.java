package com.dadam.hr.salary.service.impl;

import com.dadam.hr.salary.mapper.SalaryItemMapper;
import com.dadam.hr.salary.service.SalaryItemService;
import com.dadam.hr.salary.service.SalaryItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 급여항목 마스터 Service 구현체
 */
@Service
public class SalaryItemServiceImpl implements SalaryItemService {
    @Autowired
    private SalaryItemMapper salaryItemMapper;

    @Override
    public List<SalaryItemVO> getSalaryItemList(Map<String, Object> map) {
        List<SalaryItemVO> list = salaryItemMapper.selectSalaryItemList(map);
        for (SalaryItemVO vo : list) {
            // note 등 필요시 가공
            if (vo.getNote() == null) {
                vo.setNote("");
            }
        }
        return list;
    }
    

    @Override
    public SalaryItemVO getSalaryItem(String comId, String allowCode) {
        return salaryItemMapper.selectSalaryItem(comId, allowCode);
    }

    @Override
    public String getLastAllowCode(String comId) {
        return salaryItemMapper.selectLastAllowCode(comId);
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