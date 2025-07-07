package com.dadam.hr.emp.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;
import com.dadam.security.service.LoginUserAuthority;
import com.dadam.security.service.LoginMainAuthority;

import jakarta.servlet.http.HttpSession;

/**
 * 마이페이지 컨트롤러
 * - 내 정보 조회/수정, 프로필 이미지 등 담당
 */
@Controller
@RequestMapping("/erp/hr")
public class MyPageController {

    /** 사원 서비스 */
    @Autowired
    private EmpService empService;

    /**
     * 현재 로그인 사용자 권한 정보 조회
     * @return 권한 정보 Map
     */
    private Map<String, String> getCurrentUserInfo() {
        Map<String, String> userInfo = new HashMap<>();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return userInfo; // null 체크 추가
        }
        
        Object principal = auth.getPrincipal();

        if (principal instanceof LoginUserAuthority) {
            LoginUserAuthority user = (LoginUserAuthority) principal;
            userInfo.put("comId", user.getComId());
            userInfo.put("deptCode", user.getDeptCode());
            userInfo.put("authority", user.getOptionCode());
            userInfo.put("empId", user.getUserId());
        } else if (principal instanceof LoginMainAuthority) {
            LoginMainAuthority user = (LoginMainAuthority) principal;
            userInfo.put("comId", user.getComId());
            userInfo.put("deptCode", user.getDeptCode());
            userInfo.put("authority", user.getAuthority());
            // 관리자의 경우 comId를 empId로 사용 (임시)
            userInfo.put("empId", user.getComId());
        }
        
        return userInfo;
    }

    /**
     * 마이페이지 화면 반환
     * @param model 뷰 모델
     * @param session 세션
     * @return 마이페이지 뷰
     */
    @GetMapping("/mypage")
    public String mypage(Model model, HttpSession session) {
        // Spring Security를 통해 사용자 정보 가져오기
        Map<String, String> userInfo = getCurrentUserInfo();
        String empId = userInfo.get("empId");
        String authority = userInfo.get("authority");
        
        if (empId == null || empId.isEmpty()) {
            return "redirect:/account/login";
        }

        try {
            // 관리자인 경우 기본 정보 제공
            if ("master".equals(authority) || "admin".equals(authority)) {
                EmpVO adminUser = new EmpVO();
                adminUser.setEmpId(empId);
                adminUser.setEmpName("관리자");
                adminUser.setDeptName("관리팀");
                adminUser.setPositionName("관리자");
                adminUser.setEmail("admin@company.com");
                adminUser.setTel("010-0000-0000");
                adminUser.setAddr("서울시 강남구");
                adminUser.setAddrDetail("관리자 주소");
                model.addAttribute("user", adminUser);
                model.addAttribute("isAdmin", true);
            } else {
                // 일반 사용자 정보 조회
                EmpVO user = empService.getEmpDetail(empId);
                if (user != null) {
                    model.addAttribute("user", user);
                } else {
                    // 사용자 정보가 없으면 기본값 설정
                    EmpVO defaultUser = new EmpVO();
                    defaultUser.setEmpId(empId);
                    defaultUser.setEmpName("사용자");
                    defaultUser.setDeptName("부서");
                    defaultUser.setPositionName("직급");
                    model.addAttribute("user", defaultUser);
                }
                model.addAttribute("isAdmin", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 발생시 기본값 설정
            EmpVO defaultUser = new EmpVO();
            defaultUser.setEmpId(empId);
            defaultUser.setEmpName("사용자");
            defaultUser.setDeptName("부서");
            defaultUser.setPositionName("직급");
            model.addAttribute("user", defaultUser);
            model.addAttribute("isAdmin", false);
        }

        return "hr/mypage";
    }

    /**
     * 프로필 업데이트 API
     * @param profileImage 프로필 이미지
     * @param tel 연락처
     * @param email 이메일
     * @param addr 주소
     * @param addrDetail 상세주소
     * @param session 세션
     * @return 성공/실패 메시지
     */
    @PostMapping("/api/mypage/update")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateProfile(
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam("tel") String tel,
            @RequestParam("email") String email,
            @RequestParam("addr") String addr,
            @RequestParam("addrDetail") String addrDetail,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();
        
        try {
            // Spring Security를 통해 사용자 정보 가져오기
            Map<String, String> userInfo = getCurrentUserInfo();
            String empId = userInfo.get("empId");
            
            if (empId == null || empId.isEmpty()) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.badRequest().body(response);
            }

            // 사용자 정보 조회
            EmpVO user = empService.getEmpDetail(empId);
            if (user == null) {
                response.put("success", false);
                response.put("message", "사용자 정보를 찾을 수 없습니다.");
                return ResponseEntity.badRequest().body(response);
            }

            // 프로필 이미지 업로드 처리
            String profileImageName = null;
            if (profileImage != null && !profileImage.isEmpty()) {
                profileImageName = uploadProfileImage(profileImage, empId);
                if (profileImageName != null) {
                    user.setProfileImgPath(profileImageName);
                }
            }

            // 사용자 정보 업데이트
            user.setTel(tel);
            user.setEmail(email);
            user.setAddr(addr);
            user.setAddrDetail(addrDetail);

            // 데이터베이스 업데이트
            boolean updateResult = empService.updateEmp(user);
            
            if (updateResult) {
                response.put("success", true);
                response.put("message", "프로필이 성공적으로 업데이트되었습니다.");
                if (profileImageName != null) {
                    response.put("profileImage", profileImageName);
                }
            } else {
                response.put("success", false);
                response.put("message", "프로필 업데이트에 실패했습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "서버 오류가 발생했습니다: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 프로필 이미지 업로드
     * @param file 이미지 파일
     * @param empId 사원번호
     * @return 파일명
     */
    private String uploadProfileImage(MultipartFile file, String empId) {
        try {
            // 업로드 디렉토리 생성
            String uploadDir = "uploads/profile/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 파일명 생성 (empId_timestamp.확장자)
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            
            String filename = empId + "_" + System.currentTimeMillis() + extension;
            Path filePath = Paths.get(uploadDir, filename);

            // 파일 저장
            Files.write(filePath, file.getBytes());

            return filename;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 사용자 정보 조회 API
     * @param session 세션
     * @return 사용자 정보/성공여부
     */
    @GetMapping("/api/mypage/user")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUserInfo(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Spring Security를 통해 사용자 정보 가져오기
            Map<String, String> userInfo = getCurrentUserInfo();
            String empId = userInfo.get("empId");
            
            if (empId == null || empId.isEmpty()) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.badRequest().body(response);
            }

            EmpVO user = empService.getEmpDetail(empId);
            if (user != null) {
                response.put("success", true);
                response.put("user", user);
            } else {
                response.put("success", false);
                response.put("message", "사용자 정보를 찾을 수 없습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "서버 오류가 발생했습니다.");
        }

        return ResponseEntity.ok(response);
    }
} 