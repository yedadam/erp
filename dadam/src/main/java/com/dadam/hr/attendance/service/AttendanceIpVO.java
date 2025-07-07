package com.dadam.hr.attendance.service;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 출퇴근 IP 관리 VO
 */
@Data
public class AttendanceIpVO {
    /** IP 관리코드 */
    private String ipCode;
    /** 회사ID */
    private String comId;
    /** IP 주소 */
    private String ipAddress;
    /** IP 설명 */
    private String ipDescription;
    /** 허용여부 (Y/N) */
    private String isAllowed;
    /** 등록자ID */
    private String registId;
    /** 등록일시 */
    private LocalDateTime registDate;
    /** 수정자ID */
    private String updateId;
    /** 수정일시 */
    private LocalDateTime updateDate;
    /** 비고 */
    private String note;
} 