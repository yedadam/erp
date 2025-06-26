package com.dadam.hr.emp.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    
    private final JavaMailSender mailSender;

    /**
     * 사원 등록 완료 안내 메일 발송
     * @param to 받는 사람 이메일 주소
     * @param empName 사원명
     */
    public void sendEmpRegisterMail(String to, String empName) {
        String subject = "[다담 ERP] " + empName + "님, 사원 등록이 완료되었습니다.";
        String text = "<html><body>"
                    + "<h2>" + empName + "님, 안녕하세요.</h2>"
                    + "<p>다담 ERP 시스템에 사원 등록이 정상적으로 완료되었습니다.</p>"
                    + "<p>감사합니다.</p>"
                    + "</body></html>";
        
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // true: HTML 사용
            mailSender.send(mimeMessage);
            log.info("메일 발송 성공: " + to);
        } catch (Exception e) {
            log.error("메일 발송 실패: " + to, e);
        }
    }
} 