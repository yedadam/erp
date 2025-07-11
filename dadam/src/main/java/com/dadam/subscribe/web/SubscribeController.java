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

import com.dadam.subscribe.service.SubscribeService;
import com.dadam.subscribe.service.SubscribeVO;

/**
 * @author 신현욱
 * @since 2025.06.18
 * @desc 구독 신청 폼이랑 계약서 저장 처리하는 컨트롤러임
 * @history
 *   - 2025.06.18 신현욱 : 최초 작성 (구독 폼 페이지 보여주기)
 *   - 2025.06.21 신현욱 : 계약서 html 파일 저장 기능이랑 서명 이미지 삽입 추가함
 */
@Controller
@RequestMapping("/main")
public class SubscribeController {

    @Autowired
    SubscribeService service;

    /**
     * @desc 구독 신청 폼 페이지로 이동
     * @param model 메뉴 목록 전달하기 위한 모델 객체
     * @return String 뷰 경로 (/subscribe/subscribe)
     */
    @GetMapping("/subscribe")
    public String subCribeForm(Model model) {
        List<SubscribeVO> result = service.menuList();
        model.addAttribute("list", result);
        return "subscribe/subscribe";
    }

    /**
     * @desc 계약서 html + 서명 이미지 파일 저장 처리
     * @param payload { html, fileName, signatureImage, signatureImageName } 담긴 Map
     * @implNote 
     *   - base64 인코딩된 서명 이미지를 /static/signatures 에 저장
     *   - html 내부에 <img src="/signatures/..."> 태그 넣고
     *     templates/contracts 에 html 파일로 저장함
     */
    @PostMapping("/subsdown")
    public ResponseEntity<String> subsDown(@RequestBody Map<String, String> payload) {
        String html = payload.get("html");
        String fileName = payload.get("fileName");
        String signatureImage = payload.get("signatureImage"); // base64 PNG
        String signatureImageName = payload.get("signatureImageName");

        // 서명 이미지 저장
        try {
            if (signatureImage != null && signatureImage.startsWith("data:image/png;base64,")) {
                String base64Image = signatureImage.split(",")[1];
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                Path imagePath = Paths.get(System.getProperty("user.dir"), "uploads/signatures", signatureImageName);
                Files.createDirectories(imagePath.getParent());
                Files.write(imagePath, imageBytes);

                // 이미지 태그 만들어서 html 끝부분에 넣기
                String imageTag = "<img src=\"/uploads/signatures/" + signatureImageName + "\" style=\"width:200px;height:auto;\">";
                html = html.replace("</div></div></div>", imageTag + "</div></div></div>");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서명 이미지 저장 실패");
        }

        // 계약서 html 파일 저장
        try {
        	Path outputPath = Paths.get(System.getProperty("user.dir"), "uploads/contracts", fileName);
            Files.createDirectories(outputPath.getParent());
            Files.write(outputPath, html.getBytes(StandardCharsets.UTF_8));
            return ResponseEntity.ok("계약서 저장 성공");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("계약서 저장 실패");
        }
    }
}
