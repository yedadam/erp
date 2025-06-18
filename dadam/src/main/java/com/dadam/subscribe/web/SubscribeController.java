package com.dadam.subscribe.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping("/subscribe")
	public String subCribeForm() {
		return "/subscribe/subscribe";
	}
}
