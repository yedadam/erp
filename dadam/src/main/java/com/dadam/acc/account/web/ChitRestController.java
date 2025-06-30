package com.dadam.acc.account.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.acc.account.service.ChitService;
import com.dadam.acc.account.service.ChitVO;

@RestController
@RequestMapping("/erp/chit")
public class ChitRestController {
	
	@Autowired ChitService chitService;
	
	@GetMapping("/chitFindAll")
	public List<ChitVO> getChitFindAll(){
		return chitService.chitFindAll();
	}
	
	@GetMapping("/acctCodeFind")
    public List<Map<String, String>> acctCodeFind(@RequestParam("keyword") String keyword) {
        return chitService.acctCodeFindByCode(keyword);
    }
	
	@GetMapping("/indTypeFindByCode")
	public List<Map<String, String>> indTypeFindByCode(@RequestParam("keyword") String keyword){
		return chitService.indTypeFindByCode(keyword);
	}
	
	@GetMapping("/chitTypeFindByCode")
	public List<Map<String, String>> chitTypeFindByCode(@RequestParam("keyword") String keyword){
		return chitService.chitTypeFindByCode(keyword);
	}
	
	@PostMapping("/saveAll")
    public Map<String, Object> saveAll(@RequestBody ChitVO vo) {
        Map<String, Object> result = new HashMap<>();

        try {
            // 실제 저장 로직 수행
            System.out.println("VO 확인: " + vo);

            result.put("result", "success");
            result.put("message", "전표 저장 성공!");
        } catch (Exception e) {
            result.put("result", "fail");
            result.put("message", e.getMessage());
        }

        return result;
    }


}
