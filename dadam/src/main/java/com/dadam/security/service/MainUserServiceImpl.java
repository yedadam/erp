package com.dadam.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dadam.security.mapper.UserMapper;

@Service
public class MainUserServiceImpl {
	@Autowired
	UserMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public int idCheck(String comId) {
		return mapper.checkId(comId);
	}
	
	public int insertId(ErpUserVO vo) {
		vo.setPassword(passwordEncoder.encode(vo.getPassword())); 
		return mapper.insertId(vo);
	}
}
