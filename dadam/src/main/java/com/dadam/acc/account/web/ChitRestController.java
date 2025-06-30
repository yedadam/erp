package com.dadam.acc.account.web;

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
	 public void saveAll(@RequestBody ChitVO chit) {
		System.out.println("👉 INSERT할 데이터: " + chit);
		chitService.saveAll(chit);
	}


}
