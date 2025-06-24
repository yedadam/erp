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

        // 요청 JSON 생성
        Map<String, Object> message = Map.of("text", prompt);
        Map<String, Object> parts = Map.of("parts", List.of(message));
        Map<String, Object> body = Map.of("contents", List.of(parts));

        String json = mapper.writeValueAsString(body);

        // 요청 빌드
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                // 오류 메시지도 JSON으로 감싸서 반환
                return Map.of("text", "오류: " + response.body().string());
            }

            String responseBody = response.body().string();
            Map<?, ?> result = mapper.readValue(responseBody, Map.class);
            List<?> candidates = (List<?>) result.get("candidates");

            if (candidates != null && !candidates.isEmpty()) {
                Map<?, ?> first = (Map<?, ?>) candidates.get(0);
                Map<?, ?> content = (Map<?, ?>) first.get("content");
                List<?> responseParts = (List<?>) content.get("parts");

                if (responseParts != null && !responseParts.isEmpty()) {
                    Object part = responseParts.get(0);
                    if (part instanceof String) {
                        return Map.of("text", (String) part);
                    } else if (part instanceof Map) {
                        Map<?, ?> partMap = (Map<?, ?>) part;
                        Object text = partMap.get("text");
                        return Map.of("text", text != null ? text.toString() : "응답 없음");
                    }
                }
            }

            return Map.of("text", "응답 없음");
        }
    }
}
