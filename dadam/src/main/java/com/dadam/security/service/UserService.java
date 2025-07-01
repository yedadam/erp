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
/* 
 * @author 신현욱
 * @since 2025.06.18
 * @desc 로그인 처리
 * @history
 *   - 2025.06.18 신현욱: 최초 작성
 */
@Service
public class UserService implements UserDetailsService{
   
   @Autowired
   private UserMapper userMapper;

   
   /**
    * Spring Security에서 사용자의 인증 정보를 로딩하는 메서드
    * 요청된 URL 경로에 따라 ERP 로그인과 메인 로그인 구분 처리를 함
    * ERP 로그인 시에는 username에 사원ID및회사ID 형태로 전달된 값을 파싱하여 처리
    *
    * @param username 사용자 ID 또는 '사원ID|회사ID' 형태의 복합 값
    * @return UserDetails 인증에 사용할 사용자 정보 객체
    * @throws UsernameNotFoundException 사용자를 찾을 수 없거나 잘못된 요청일 경우 발생
    */
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // 현재 요청 URI 가져오기
       HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
               .currentRequestAttributes()).getRequest();
       String uri = request.getRequestURI();

       EmployeesVO userVO = null;
       ErpUserVO mainUserVO = null;
       //먼저 main url로 시작하면 발동
       if (uri.startsWith("/main")) {
       	System.out.println("이름"+username);
       	// main 로그인용
       	mainUserVO = userMapper.loginForMain(username); 
       //먼저 erp url로 시작하면 발동
       } else if (uri.startsWith("/erp")) {
    	   //userName값 자르기
    	   String[] parts = username.split("\\|");
    	    if (parts.length != 2) {
    	        throw new UsernameNotFoundException("아이디 또는 회사 ID 형식이 올바르지 않습니다.");
    	    }
       	// erp 로그인용
    	   
           userVO = userMapper.loginForErp(parts[0],parts[1]);
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
