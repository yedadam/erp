package com.dadam.hr.attendance.service;

import java.time.LocalDate;
import java.time.Period;

/**
 * 연차 계산 유틸리티
 */
public class AnnualLeaveUtil {
    /**
     * 입사일 기준 연차 총일수 계산
     * 1년 미만: 매월 1일씩, 1년 이상: 15일 + 근속연수에 따라 추가(최대 25일)
     * @param hireDate - 입사일
     * @param now - 기준일
     * @return 연차 총일수
     */
    public static int calculateAnnualLeaveTotal(LocalDate hireDate, LocalDate now) {
        int years = Period.between(hireDate, now).getYears();
        if (years < 1) {
            int months = Period.between(hireDate, now).getMonths() + 1;
            return months;
        } else {
            int extra = Math.min(years - 1, 10); // 최대 10일 추가
            return 15 + extra;
        }
    }
} 