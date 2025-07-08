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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.acc.account.service.AccountService;
import com.dadam.acc.account.service.AccountVO;


//RestController를 사용하면 결과값을 제이슨형식으로 반환된다.
@RestController
//uri 경로에 erp는 무조건 넣고 sample 넣는 기능은 각자 부서를 넣으면 된다.
//예를들어 인사 :/erp/hr  영업:/erp/sales  회계:/erp/acc
//재고:/erp/inventory 기준정보:/erp/admin
@RequestMapping("/erp/accounting")  
public class AccountRestController {
	
	@Autowired
	AccountService accountService;
	
    // ✅ comId를 요청 파라미터로 받는 구조 (chit처럼)
    @GetMapping("/accFindAll")
    public List<AccountVO> getAccFindAll() {
        return accountService.accFindAll();
    }

    @GetMapping("/accFindByType")
    public List<AccountVO> getAccByType(@RequestParam(required = false) String acctType) {
        // 로그인 정보 기반 comId 사용
        // 서비스에서 initAuthInfo()로 comId를 얻어야 하므로, 임시로 null 전달
        return accountService.accFindByType(acctType, null);
    }

    // ✅ 저장은 comId 없이 요청 → ServiceImpl에서 initAuthInfo() 처리
    @PostMapping("/saveAll")
    @ResponseBody
    public Map<String, Object> saveAccounts(@RequestBody AccountVO accountVO) {
        Map<String, Object> result = new HashMap<>();
        try {
            accountService.saveAll(accountVO);
            result.put("result", "success");
            result.put("message", "저장이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            result.put("result", "fail");
            result.put("message", "유효성 오류: " + e.getMessage());
        }
        return result;
    }

    // 자동완성 관련: comId 없이 처리 가능
    @GetMapping("/api/account/type")
    public List<String> getAcctTypes() {
        return accountService.getAcctTypes();
    }

    @GetMapping("/api/account/class")
    public List<String> getAcctClasses(@RequestParam String typeCode) {
        System.out.println("중분류 전달값: " + typeCode);
        return accountService.getAcctClasses(typeCode);
    }

    @GetMapping("/api/account/subclass")
    public List<String> getAcctSubClasses(@RequestParam String classCode) {
        System.out.println("소분류 전달값: " + classCode);
        return accountService.getAcctSubClasses(classCode);
    }

    @GetMapping("/accountSearch")
    public List<AccountVO> accountSearch(@RequestParam Map<String, Object> params) {
        return accountService.accountSearch(params);
    }
}
