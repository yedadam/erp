package com.dadam.acc.credit.web;

import com.dadam.acc.credit.service.TransactionsService;
import com.dadam.acc.credit.service.TransactionsVO;
import com.dadam.standard.vender.mapper.VenderMapper;
import com.dadam.standard.vender.service.VenderVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/erp/accounting")
public class TransactionsRestController {
	
    @Autowired
    TransactionsService transactionsService;

    @Autowired
    private VenderMapper venderMapper;

    @GetMapping("/transactions")
    public List<TransactionsVO> getAll(String comId) {
        return transactionsService.getAll(comId);
    }


    @PostMapping("/transactionsData")
    public Map<String, Object> add(@RequestBody Map<String, List<TransactionsVO>> payload) {
        List<TransactionsVO> createdRows = payload.get("createdRows");
        for (TransactionsVO vo : createdRows) {
            transactionsService.add(vo);
        }
        return Map.of("result", "success", "message", "입출금 등록 완료");
    }

    @GetMapping("/venderAll")
    public List<VenderVO> venderAll(@RequestParam String comId,
                                    @RequestParam(required = false, defaultValue = "") String type,
                                    @RequestParam(required = false, defaultValue = "") String value) {
        return transactionsService.findVenders(comId, type, value);
    }
} 	