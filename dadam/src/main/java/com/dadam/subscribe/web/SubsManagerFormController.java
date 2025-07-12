package com.dadam.subscribe.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dadam.common.JasperDownCommon;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author 신현욱
 * @since 2025.06.24
 * @desc 구독 관리자 화면 처리랑 보고서 출력 담당하는 컨트롤러임
 * @history
 *   - 2025.06.24  신현욱 : 최초 작성. 계약서 html 미리보기 기능 넣음
 *   - 2025.06.25 신현욱 : Jasper 보고서 출력 기능 추가함
 */
@Controller
@RequestMapping("/main")
public class SubsManagerFormController {

    /**
     * @desc 구독 관리자 페이지로 이동
     * @return String : 뷰 이름 (/subscribe/manager)
     */
    @GetMapping("/subManager")
    public String subManagerForm() {
        return "subscribe/manager";
    }

    /**
     * @desc 계약서 html 미리보기 기능
     * @param constImage : 계약서 파일명 (예: contract1.html)
     * @return String : contracts 폴더 안에 html 뷰 이름 리턴
     * @implNote ".html" 확장자 제거해서 thymeleaf 뷰로 인식하게 처리함contracts
settlementEle
signatures
     */
    @GetMapping("/preview")
    public void constPreview(@RequestParam String constImage, HttpServletResponse response) throws IOException {
        //파일 경로 설정 (운영서버의 실제 경로)
    	Path filePath = Paths.get(System.getProperty("user.dir"), "uploads/contracts", constImage);
    	System.out.println("경로"+filePath);
        if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
        	System.out.println(constImage+"컨스트");
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

    @Autowired
    private JasperDownCommon jasperDownCommon;

    /**
     * @desc Jasper보고서 다운로드 처리
     * @param subsCode : 구독 코드
     * @param response : HttpServletResponse
     * @return ModelAndView : jasperDownCommon 뷰로 파일 내려줌
     * @throws Exception 예외 발생 시 던짐
     */
    @RequestMapping("/report")
    public ModelAndView report2(@RequestParam String subsCode , HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();

        // jasper 커스텀뷰 세팅
        mv.setView(jasperDownCommon);

        // 보고서 템플릿경로 지정
        mv.addObject("filename", "/report/taxinvoice.jasper");
        String reportDirPath = this.getClass().getResource("/report/").getPath();
        // 보고서 파라미터 넣기
        HashMap<String, Object> map = new HashMap<>();
        map.put("p_subsCode", subsCode);
        map.put("REPORT_DIR", reportDirPath);
        mv.addObject("param", map);

        return mv;
    }
}
