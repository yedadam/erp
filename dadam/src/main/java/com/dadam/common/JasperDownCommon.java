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
		Connection conn = datasource.getConnection();
	String reportFile = (String)model.get("filename");
	@SuppressWarnings("unchecked")
	Map<String, Object> map = (Map<String, Object>) model.get("param");
	System.out.println(map);
	InputStream jasperStream = getClass().getResourceAsStream(reportFile);
	JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
	// 다운로드용 응답 설정
	response.setContentType("application/pdf");
	response.setHeader("Content-Disposition", "attachment; filename=\"taxinvoice.pdf\"");
	JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream()); 
	}
}
