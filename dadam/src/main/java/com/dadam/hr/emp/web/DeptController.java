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

/**
 * 부서 관리 API 컨트롤러
 */
@RestController
@RequestMapping("/erp/hr")
public class DeptController {

    private final DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    /**
     * 부서 목록 조회
     * @return 부서 리스트
     */
    @GetMapping("/dept")
    public List<DeptVO> getDeptList() {
        return deptService.findAllDepartments();
    }

    /**
     * 부서 등록
     * @param dept - 부서 정보
     * @return 등록 결과
     */
    @PostMapping("/deptInsert")
    public int insertDept(@RequestBody DeptVO dept) {
        return deptService.insertDepartment(dept);
    }

    /**
     * 부서 수정
     * @param dept - 부서 정보
     * @return 수정 결과
     */
    @PostMapping("/deptUpdate")
    public int updateDept(@RequestBody DeptVO dept) {
        return deptService.updateDepartment(dept);
    }

    /**
     * 부서 삭제
     * @param param - 부서코드
     * @return 삭제 결과
     */
    @PostMapping("/deptDelete")
    public int deleteDept(@RequestBody Map<String, String> param) {
        return deptService.deleteDepartment(param.get("deptCode"));
    }

    /**
     * 부서 상세 조회
     * @param deptCode - 부서코드
     * @return 부서 정보
     */
    @GetMapping("/deptDetail")
    public DeptVO getDeptDetail(@RequestParam String deptCode) {
        return deptService.findDepartmentByCode(deptCode);
    }
} 