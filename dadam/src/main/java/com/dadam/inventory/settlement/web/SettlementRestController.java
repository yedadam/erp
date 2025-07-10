package com.dadam.inventory.settlement.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.inventory.settlement.service.SettlementService;
import com.dadam.inventory.settlement.service.SettlementVO;
import com.dadam.security.service.EmployeesVO;

import jakarta.servlet.ServletContext;

@RestController
@RequestMapping("/erp/inventory")
public class SettlementRestController {
	
	
	@Autowired
	SettlementService service;
	@Autowired
	ServletContext servletContext;
	
	@GetMapping("/settleEmpList")
	public List<EmployeesVO> seettleEmpList() {
		List<EmployeesVO> result= service.settleEmployeesList();
		return result;
	}
	
	@GetMapping("/settleAutoList")
	public List<SettlementVO> settleAutoList(){
		List<SettlementVO> result =service.autoSettlementList();
		return result;
	} 
	 //이번달 결산 했는지체크
	@GetMapping("/monthCheck")
	public int monthCheck() {
		int result = service.monthCheck();
		return result;
	}
	@PostMapping("/settleAdd")
	public String settleAdd(@RequestBody  List<SettlementVO> vo) {
		String r = service.settlementAdd(vo);
		return r;
	}
	//파일 저장
	@PostMapping("/fileAdd")
	public ResponseEntity<String> fileAdd(@RequestBody Map<String,String> payload){
		String html = payload.get("html");
		String fileName = payload.get("fileName");
		
		try {
			Path outputPath = Paths.get(System.getProperty("user.dir"), "settlementEle", fileName);
			Files.createDirectories(outputPath.getParent());
			Files.write(outputPath, html.getBytes(StandardCharsets.UTF_8));
			return ResponseEntity.ok("결재서 저장 성공");
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("계약서 저장 실패");
		}
	}
	//결재 DB저장
	@PostMapping("/eleAdd")
	public int eleAdd(@RequestBody List<SettlementVO> vo) {
	   int result = service.eleAdd(vo);
	   return result;
	}
	
	//재고 결산 List 조회
	@GetMapping("/setlleList")
	public List<SettlementVO> settleList (@RequestParam Map<String,Object> map){
		List<SettlementVO> result = service.settleList(map);
		return result;
	}
	
	@GetMapping("/mineEle")
	public List<SettlementVO> mineEle (){
		List<SettlementVO> result = service.mineEle();
		return result;
	}
	
	@PostMapping("/insertSign")
	public ResponseEntity<String> insertSignature(@RequestBody SettlementVO vo) {

	    try {
	        // 기본값 체크
	        if (vo.getFileImage() == null || vo.getSignImage() == null) {
	            return ResponseEntity.badRequest().body("fileImage 또는 signImage가 누락되었습니다.");
	        }

	        // 실제 경로 지정
	        Path path = Paths.get(System.getProperty("user.dir"), "settlementEle", vo.getFileImage());

	        // 파일 존재 확인
	        if (!Files.exists(path)) {
	            return ResponseEntity.badRequest().body("HTML 파일이 존재하지 않습니다.");
	        }

	        // HTML 읽기
	        String html = Files.readString(path, StandardCharsets.UTF_8);

	        // 서명 이미지 태그 생성 (base64 이미지)
	        String signImgTag = "<img src='" + vo.getSignImage() + "' style='width: 100px; height: auto;'>";

	        // SIGN_PLACEHOLDER가 없으면 에러 반환
	        if (!html.contains("<!-- "+vo.getAppId()+" -->")) {
	            return ResponseEntity.badRequest().body("HTML에 SIGN_PLACEHOLDER 주석이 없습니다.");
	        }

	        // 주석 위치에 서명 이미지 삽입
	        html = html.replace("<!-- "+vo.getAppId()+" -->", signImgTag);

	        // HTML 다시 저장 (덮어쓰기)
	        Files.write(path, html.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);

	        return ResponseEntity.ok("전자서명 삽입 완료");

	    } catch (IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.internalServerError().body("파일 처리 중 오류 발생");
	    }
	}
	
	//상태값 변환 프로시저
	@PostMapping("/statusUpdate")
	public void statusUpdate(@RequestBody SettlementVO vo) {
		service.prcEletronicUpdate(vo);
		
	}
	
}
