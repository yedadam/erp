package com.dadam.hr.emp.web;

import com.dadam.hr.emp.service.DeptService;
import com.dadam.hr.emp.service.DeptVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/erp/hr/api/dept")
public class DeptController {

    private final DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping("/list")
    public List<DeptVO> getDeptList() {
        return deptService.findAllDepartments();
    }

    @PostMapping("/insert")
    public int insertDept(@RequestBody DeptVO dept) {
        return deptService.insertDepartment(dept);
    }

    @PostMapping("/update")
    public int updateDept(@RequestBody DeptVO dept) {
        return deptService.updateDepartment(dept);
    }

    @PostMapping("/delete")
    public int deleteDept(@RequestBody Map<String, String> param) {
        return deptService.deleteDepartment(param.get("deptCode"));
    }

    @GetMapping("/detail")
    public DeptVO getDeptDetail(@RequestParam String deptCode) {
        return deptService.findDepartmentByCode(deptCode);
    }
} 