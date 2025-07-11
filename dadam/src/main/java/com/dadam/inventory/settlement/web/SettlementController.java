package com.dadam.inventory.settlement.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/erp/inventory")
public class SettlementController {
	
	
	@GetMapping("/settlementForm")
	public String settlementForm() {
		return "inventory/settlement";
	}
	
	@GetMapping("/setTest")
	public String setTest() {
		return "settlementEle/settlement_1751784710304.html";
	}
	
	@GetMapping("/preview")
	public void preview(@RequestParam String setImage, HttpServletResponse response) throws IOException {
		Path filePath = Paths.get(System.getProperty("user.dir"), "uploads/settlementEle", setImage);
		System.out.println(setImage);
		if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //컨텐츠 타입 설정
        response.setContentType("text/html;charset=UTF-8");

        // 파일 내용을 스트림으로 복사해서 응답에 씀
        try (InputStream in = Files.newInputStream(filePath);
             ServletOutputStream out = response.getOutputStream()) {
            in.transferTo(out);
        }
	}
}
