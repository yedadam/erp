package com.dadam.security.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;
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
public class LoginUserAuthority implements UserDetails {
    private EmployeesVO userVO;

    public LoginUserAuthority(EmployeesVO userVO) {
        this.userVO = userVO;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	List<GrantedAuthority> auth = new ArrayList<>();
		auth.add(new SimpleGrantedAuthority(userVO.getOptionCode()));
		auth.add(new SimpleGrantedAuthority(userVO.getAuthCode()));
		return auth;
    }

    @Override
    public String getPassword() {
        return userVO.getPwd(); // userVO의 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return userVO.getEmpName(); // userVO의 아이디 반환
    }
    public String getEmpName() {
        return userVO.getEmpName(); // userVO의 아이디 반환
    }
    
    
    public String getMainName() {
    	return ""; // userVO의 아이디 반환
    }
    public String getMainId() {
    	return ""; // userVO의 아이디 반환
    }
    public String getUserId() {
    	return userVO.getEmpId();
    }
    public String getComId(){
    	return userVO.getComId();
    }
    public String getDeptCode() {
    	return userVO.getDeptCode();
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
    //계정 만료
    @Override
    public boolean isAccountNonExpired() {
    	Date exp = userVO.getSubsExpiration();
        if (exp == null) return true;
        return !exp.toLocalDate().isBefore(LocalDate.now());
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