package com.dadam.subscribe.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service // 이게 있어야 Spring Bean으로 등록됨
public class SubscribeServiceImpl {
	//정기결제 스케줄러
	
		@Value("${portone.imp_key}")
	    private String impKey;

	    @Value("${portone.imp_secret}")
	    private String impSecret;
	    String BASE_URL = "https://api.iamport.kr";
	    @Autowired
	    private RestTemplate restTemplate; // 이제 오류 안남
	    
	    public String getAccessToken() {
	        String url = BASE_URL + "/users/getToken";
	        
	        Map<String, String> request = new HashMap<>();
	        request.put("imp_key", impKey);
	        request.put("imp_secret", impSecret);
	        
	        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
	        Map<String, Object> responseBody = response.getBody();
	        Map<String, String> responseData = (Map<String, String>) responseBody.get("response");
	        
	        return responseData.get("access_token");
	    }
	   
	    @Test
	    public String chargeSubscription(String customerUid, int amount) {
	        String accessToken = getAccessToken();
	        String url = BASE_URL + "/subscribe/payments/again";
	        
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", "Bearer " + accessToken);
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        
	        Map<String, Object> requestBody = new HashMap<>();
	        requestBody.put("customer_uid", "user_1750422595338");
	        requestBody.put("merchant_uid", "sub_" + System.currentTimeMillis());
	        requestBody.put("amount", 100);
	        requestBody.put("name", "정기구독료");
	        
	        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
	        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
	        log.info("결제 완료: {}", response.getBody());
	        return "결제 완료: " + response.getBody();
	    }
}
