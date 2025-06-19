package com.dadam.subscribe.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dadam.subscribe.service.SubscribeService;
import com.dadam.subscribe.service.SubscribeVO;
/* 
 * @author 신현욱
 * @since 2025.06.18
 * @desc 구독 폼태그 완성
 * @history
 *   - 2025.06.18 신현욱: 최초 작성
 */
@RequestMapping("/main")
@Controller
public class SubscribeController {
	
	@Autowired
	SubscribeService service;
	
	//Form
	@GetMapping("/subscribe")
	public String subCribeForm(Model model) {
		List<SubscribeVO> result = service.menuList();
		model.addAttribute("list",result);
		return "/subscribe/subscribe";
	}
	
	//html다운로드
	@PostMapping("/subsdown")
	public ResponseEntity<String> subsDown(@RequestBody Map<String,String> payload) {
		
		String html = payload.get("html");
	    String fileName = payload.get("fileName");
	    String signatureImage = payload.get("signatureImage"); // base64 PNG
	    String signatureImageName = payload.get("signatureImageName");
	    
	    // 이미지 저장
	    try {
	        if (signatureImage != null && signatureImage.startsWith("data:image/png;base64,")) {
	            String base64Image = signatureImage.split(",")[1];
	            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
	            Path imagePath = Paths.get("src/main/resources/static/signatures", signatureImageName);
	            Files.createDirectories(imagePath.getParent());
	            Files.write(imagePath, imageBytes);

	            // HTML 안에 이미지 삽입 (static 경로로 접근 가능)
	            String imageTag = "<img src=\"/signatures/" + signatureImageName + "style=\"width:200px;height:auto;\">";
	            html = html.replace("</div></div></div>", imageTag + "</div></div></div>"); // 계약서 서명 영역 끝에 삽입
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서명 이미지 저장 실패");
	    }

	    // HTML 저장
	    try {
	        Path outputPath = Paths.get("src/main/resources/templates/contracts", fileName);
	        Files.createDirectories(outputPath.getParent());
	        Files.write(outputPath, html.getBytes(StandardCharsets.UTF_8));
	        return ResponseEntity.ok("계약서 저장 성공");
	    } catch (IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("계약서 저장 실패");
	    }
	}
	
	@GetMapping("/test-post")
	public String testPost() {
	    return "/contracts/contract_1750334247555";
	}
}
