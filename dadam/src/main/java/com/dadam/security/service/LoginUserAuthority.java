package com.dadam.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUserAuthority implements UserDetails {
    private UserVO userVO;

    public LoginUserAuthority(UserVO userVO) {
        this.userVO = userVO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	List<GrantedAuthority> auth = new ArrayList<>();
		//auth.add(new SimpleGrantedAuthority(userVO.getEntMemberCode()));
		return auth;
    }

    @Override
    public String getPassword() {
        return userVO.getPassword(); // userVO의 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return userVO.getUsername(); // userVO의 아이디 반환
    }
    
    public String getName() {
    	return userVO.getName();
    }
    
    public String getMainName() {
    	return ""; // userVO의 아이디 반환
    }
    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았다고 가정
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠기지 않았다고 가정
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명이 만료되지 않았다고 가정
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화되어 있다고 가정
    }
  
    
}