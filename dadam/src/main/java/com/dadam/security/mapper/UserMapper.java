package com.dadam.security.mapper;

import org.apache.ibatis.annotations.Param;

import com.dadam.security.service.MainUserVO;
import com.dadam.security.service.UserVO;

public interface UserMapper {
	MainUserVO loginForMain(@Param("userId") String userId);
	public UserVO loginForErp(String username);
}
