package com.dadam.acc.account.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//RestController를 사용하면 결과값을 제이슨형식으로 반환된다.
@RestController
//uri 경로에 erp는 무조건 넣고 sample 넣는 기능은 각자 부서를 넣으면 된다.
//예를들어 인사 :/erp/hr  영업:/erp/sales  회계:/erp/acc
//재고:/erp/inventory 기준정보:/erp/admin
@RequestMapping("/erp/acc")  
public class AccountRestController {

}
