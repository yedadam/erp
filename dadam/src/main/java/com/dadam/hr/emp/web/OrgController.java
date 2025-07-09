package com.dadam.hr.emp.web;

import com.dadam.hr.emp.service.DeptService;
import com.dadam.hr.emp.service.OrgNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 조직도 컨트롤러
 * - 조직도 화면 반환 담당
 */
@RestController
@RequestMapping("/erp/hr")
public class OrgController {
    @Autowired
    private DeptService deptService;

    /**
     * 조직도 트리 반환
     * @return 조직 트리
     */
    @GetMapping("/org")
    public OrgNode getOrgTree() {
        return deptService.getOrgTree();
    }

    // 화면 반환 메서드(/org-tree)는 OrgViewController로 분리
} 