package com.dadam.common.mapper;

import java.util.List;

import com.dadam.common.service.CodeVO;

public interface CodeMapper {
		List<CodeVO> selectCode(String mainCode);  //공통코드조회 
}
