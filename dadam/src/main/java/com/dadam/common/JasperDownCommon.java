package com.dadam.common;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Component
public class JasperDownCommon extends AbstractView{
	@Autowired
	DataSource datasource;   
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 // DB 연결
        Connection conn = datasource.getConnection();

        // .jrxml 경로 받기
        String jrxmlPath = (String) model.get("filename");

        // 파라미터 맵 받기
        @SuppressWarnings("unchecked")
        Map<String, Object> paramMap = (Map<String, Object>) model.get("param");

        // .jrxml 파일을 InputStream으로 읽음
        InputStream jrxmlStream = getClass().getResourceAsStream(jrxmlPath);
        if (jrxmlStream == null) {
            throw new RuntimeException("jrxml 파일을 찾을 수 없습니다: " + jrxmlPath);
        }

        // .jrxml → 컴파일
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        // 데이터 채움
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, conn);

        // PDF 응답 설정
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"세금계산서.pdf\"");
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        conn.close(); 
	}
}
