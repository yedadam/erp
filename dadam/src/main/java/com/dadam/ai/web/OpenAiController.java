package com.dadam.ai.web;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dadam.ai.service.impl.OpenAiServiceImpl;

@RequestMapping("/main")
@RestController
public class OpenAiController {

    @Autowired
    OpenAiServiceImpl service;

    @PostMapping("/chat")
    public Map<String, String> test(@RequestParam String param) throws IOException {
    	// 사이트 기능 설명 (고정 프롬프트)
    	String siteInfo = """
    			당신은 이 사이트의 AI 챗봇입니다.

    			- 사용자가 ERP 관련 기능(예: 결제, 구독, 계약서, 세금계산서 등)을 물어보면, 
    			  아래 안내 정보를 참고해서 정확하게 대답하세요.
    			- 결제에 대해서 얘기하면 정기결제와 기간결제로 나눠지고 더 자세한 정보는 http://dadam/main/subscribe
    			  에 있다고 전해주세요
    			- 어떤 기능이 있냐고 물으면 우리는 회계 인사 재고 영업으로 나눠지고 재무재표 손인계산서등 다양한 기능을 할 수 있다고 전해주세요.
    			- 그 외 일반적인 질문(예: 농담, 날씨, 인사, 일상 등)도 자유롭고 장난스럽게 응답하세요.

    			※ 이 사이트의 주요 기능:
    			- 구독 결제 및 상태 확인
    			- 세금계산서 발행
    			- 계약서 PDF 다운로드
    			- 고객센터 전화번호: 010-1234-5678
    			- 사업자 정보 확인
    			""";

        // 사용자 질문 + 사이트 정보로 프롬프트 구성
        String fullPrompt = siteInfo + "\n\n사용자 질문: " + param;

        // Gemini 호출
        return service.askGemini(fullPrompt);
    }
}
