package com.dadam.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dadam.security.mapper.UserMapper;


import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService implements UserDetailsService{
   
   @Autowired
   private UserMapper userMapper;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // 현재 요청 URI 가져오기
       HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
               .currentRequestAttributes()).getRequest();
       String uri = request.getRequestURI();

       UserVO userVO = null;
       MainUserVO mainUserVO = null;
       //먼저 main url로 시작하면 발동
       if (uri.startsWith("/main")) {
       	System.out.println("이름"+username);
       	// main 로그인용
       	mainUserVO = userMapper.loginForMain(username); 
       //먼저 erp url로 시작하면 발동
       } else if (uri.startsWith("/erp")) {
       	// erp 로그인용
           userVO = userMapper.loginForErp(username);
       //둘다 아니면 잘못된경로로 에러발생
       } else {
           throw new UsernameNotFoundException("잘못된 로그인 경로");
       }
       //안에 값이 없으면 유저를 찾을 수 었다고 뜸
       if (userVO == null && mainUserVO == null) {
           throw new UsernameNotFoundException("유저를 찾을 수 없습니다.");
       }
       //main이라면 mainUserVo 반환
		if (uri.startsWith("/main")) {
			return new LoginMainAuthority(mainUserVO);
		//erp 라면 userVO반화
		} else {
			return new LoginUserAuthority(userVO);
		}
	}
}
