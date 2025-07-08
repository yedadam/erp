package com.dadam.hr.emp.service.impl;

import com.dadam.hr.emp.service.AuthService;
import com.dadam.hr.emp.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;
    @Override
    public List<Map<String, String>> getAuthList() {
        return authMapper.getAuthList();
    }
} 