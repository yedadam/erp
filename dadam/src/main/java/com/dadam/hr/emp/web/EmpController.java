package com.dadam.hr.emp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;

@Controller
@RequestMapping("/erp/hr")
public class EmpController {

	@Autowired
	private EmpService empService;

	// 사원 리스트 페이지 반환 (화면)
	@GetMapping("/emp")
	public String empPage() {
		return "hr/emplist";
	}

	// 사원 전체 조회 (JSON)
	@GetMapping("/emplist")
	@ResponseBody
	public List<EmpVO> getEmpList() {
		return empService.getEmpList();
	}

	// 사원 상세 조회 (JSON)
	@GetMapping("/emp/{empId}")
	@ResponseBody
	public EmpVO getEmpDetail(@PathVariable String empId) {
		return empService.getEmpDetail(empId);
	}

	// 사원 등록/수정 (JSON)
	@PostMapping("/emp/save")
	@ResponseBody
	public int saveEmp(@RequestBody EmpVO empVO) {
		return empService.saveEmp(empVO);
	}

	// 사원 삭제 (JSON)
	@DeleteMapping("/emp/{empId}")
	@ResponseBody
	public int deleteEmp(@PathVariable String empId) {
		return empService.deleteEmp(empId);
	}
}