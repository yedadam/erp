package com.dadam.security.mapper;


import org.apache.ibatis.annotations.Param;

import com.dadam.security.service.EmployeesVO;
import com.dadam.security.service.ErpUserVO;

public interface UserMapper {
	public ErpUserVO loginForMain(@Param("comId") String userId);
	public EmployeesVO loginForErp(@Param("username") String username, @Param("comId") String comId);
}
