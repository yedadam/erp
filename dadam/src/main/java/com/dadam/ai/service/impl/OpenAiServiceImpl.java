package com.dadam.ai.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class OpenAiServiceImpl {

    @Value("${gemini.api.key}") 
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Map<String, String> askGemini(String prompt) throws IOException {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

        Map<String, Object> message = Map.of("text", prompt);
        Map<String, Object> parts = Map.of("parts", List.of(message));
        Map<String, Object> body = Map.of("contents", List.of(parts));

        String json = mapper.writeValueAsString(body);

        int maxRetries = 3;
        int delayMs = 1000;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(json, MediaType.parse("application/json")))
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();

                if (!response.isSuccessful()) {
                    System.err.println("Gemini API 응답 오류 (시도 " + attempt + "): " + responseBody);

                    // 503: 서버 과부하 → 재시도
                    if (response.code() == 503 && attempt < maxRetries) {
                        try {
                            Thread.sleep(delayMs * attempt); // 점점 증가하는 백오프
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        continue;
                    }

                    // 기타 오류는 즉시 반환
                    return Map.of("text", "오류: " + responseBody);
                }

                // 응답 처리
                Map<?, ?> result = mapper.readValue(responseBody, Map.class);
                List<?> candidates = (List<?>) result.get("candidates");

                if (candidates != null && !candidates.isEmpty()) {
                    Map<?, ?> first = (Map<?, ?>) candidates.get(0);
                    Map<?, ?> content = (Map<?, ?>) first.get("content");
                    List<?> responseParts = (List<?>) content.get("parts");

                    if (responseParts != null && !responseParts.isEmpty()) {
                        Object part = responseParts.get(0);
                        if (part instanceof Map) {
                            Object text = ((Map<?, ?>) part).get("text");
                            return Map.of("text", text != null ? text.toString() : "응답 없음");
                        }
                    }
                }

                return Map.of("text", "응답 없음");
            }
        }

        return Map.of("text", "서버 과부하로 요청 실패 (최대 재시도 초과)");
    }

}
