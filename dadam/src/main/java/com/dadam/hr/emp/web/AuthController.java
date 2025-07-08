package com.dadam.hr.emp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dadam.hr.emp.service.AuthService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/erp/hr")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @GetMapping("/authList")
    public List<Map<String, String>> getAuthList() {
        return authService.getAuthList();
    }
} 