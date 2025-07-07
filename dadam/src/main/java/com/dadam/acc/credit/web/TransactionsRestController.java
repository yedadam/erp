package com.dadam.acc.credit.web;

import com.dadam.acc.credit.service.TransactionsService;
import com.dadam.acc.credit.service.TransactionsVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/account")
public class TransactionsRestController {
	
    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("/transactions")
    public List<TransactionsVO> getAll() {
        return transactionsService.getAll();
    }


    @PostMapping("")
    public void add(@RequestBody TransactionsVO vo) {
        transactionsService.add(vo);
    }

    @PutMapping("")
    public void update(@RequestBody TransactionsVO vo) {
        transactionsService.update(vo);
    }


    @PostMapping("/saveAll")
    public Map<String, Object> saveAll(@RequestBody TransactionsVO vo) {
        Map<String, Object> result = new HashMap<>();
        try {
            transactionsService.saveAll(vo);
            result.put("result", "success");
            result.put("message", "저장이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            result.put("result", "fail");
            result.put("message", "유효성 오류: " + e.getMessage());
        }
        return result;
    }
} 