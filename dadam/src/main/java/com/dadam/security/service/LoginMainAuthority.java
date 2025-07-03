package com.dadam.security.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
/* 
 * @author 신현욱
 * @since 2025.06.18
 * @desc 로그인 권한 부여
 * @history
 *   - 2025.06.18 신현욱: 최초 작성
 */
public class LoginMainAuthority implements UserDetails {
    private ErpUserVO userVO;

    public LoginMainAuthority(ErpUserVO userVO) {
        this.userVO = userVO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	List<GrantedAuthority> auth = new ArrayList<>();
    	auth.add(new SimpleGrantedAuthority(userVO.getMaster()));
		return auth;
    }

    @Override
    public String getPassword() {
        return userVO.getPassword(); // userVO의 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return userVO.getComId(); // userVO의 아이디 반환
    }
    
    public String getMainName() {
    	return userVO.getName(); // userVO의 아이디 반환
    }
    
    public String getMainId() {
    	return userVO.getComId(); // userVO의 아이디 반환
    }
    public String getEmpName() {
        return ""; // userVO의 아이디 반환
    }
    public String getUserId() {
    	return "";
    }
    public String getComId(){
    	return "";
    }
    public String getDeptCode() {
    	return "de1001";
    }
    public String getOptionCode() {
    	return userVO.getOptionCode();
    }
    public String getMaster() {
    	return userVO.getMaster();
    }
    public Date getSubsExpiration() {
    	return userVO.getSubsExpiration();
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