package com.dadam.hr.attendance.web;

import com.dadam.hr.attendance.service.AttendanceService;
import com.dadam.hr.attendance.service.AttendanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 근태 관리 REST 컨트롤러
 */
@RestController
@RequestMapping("/erp/hr")
public class AttendanceRestController {
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/attendance/list")
    public Map<String, Object> getAttendanceList(@RequestParam String date, HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        List<AttendanceVO> list = attendanceService.findAttendanceList(comId, date);
        int count = list.size();
        int attendCount = (int) list.stream().filter(vo -> "정상".equals(vo.getStatus())).count();
        int lateCount = (int) list.stream().filter(vo -> "지각".equals(vo.getStatus()) || "결근".equals(vo.getStatus())).count();
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("data", list);
        res.put("count", count);
        res.put("attendCount", attendCount);
        res.put("lateCount", lateCount);
        return res;
    }
} 