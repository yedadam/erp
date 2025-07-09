package com.dadam.hr.emp.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface AuthMapper {
    public List<Map<String, String>> getAuthList();
} 