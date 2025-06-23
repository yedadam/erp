package com.dadam.report.web;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dadam.common.JasperPreviewCommon;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Controller
public class ReportController {
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private JasperPreviewCommon jasperCommon;
	
	@RequestMapping("report.do")
	public void report(@RequestParam String subsCode , HttpServletResponse response) throws Exception {
		Connection conn = dataSource.getConnection();

		// 소스 컴파일 jrxml -> jasper
		InputStream stream = getClass().getResourceAsStream("/report/taxinvoice.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(stream);

		// 파라미터 맵
		HashMap<String, Object> map = new HashMap<>();
		map.put("p_subsCode", subsCode);

		// PDF 출력
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);

		// 다운로드용 응답 설정
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"taxinvoice.pdf\"");

		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

	}
	
	@GetMapping("report2.do")
	public ModelAndView report2(@RequestParam String subsCode , HttpServletResponse response) throws Exception
	{
		ModelAndView mv = new ModelAndView();
		//뷰객체
		mv.setView(jasperCommon);
		//fileName
		mv.addObject("filename", "/report/taxinvoice.jasper");
		//파라미터 담을 저장소
		HashMap<String, Object> map = new HashMap<>();
		//파라미터값
		map.put("p_subsCode", subsCode);
		//파라미터값 전달
		mv.addObject("param", map);
		return mv;
	}
}
