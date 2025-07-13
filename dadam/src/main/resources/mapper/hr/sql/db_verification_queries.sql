-- ========================================
-- ERP 급여항목 카테고리 7가지 + 사원별 급여항목 연동
-- DB 데이터 정상 저장/조회 전체 쿼리
-- ========================================

-- 1. EMP_ALLOWANCE 테이블 구조 확인
SELECT 'EMP_ALLOWANCE 테이블 구조' as check_item FROM dual;
DESC EMP_ALLOWANCE;

-- 2. 사원별 급여항목 데이터 확인
SELECT '사원별 급여항목 데이터' as check_item FROM dual;
SELECT 
    EMP_ID as "사원번호",
    ALLOW_CODE as "급여항목코드",
    AMOUNT as "금액",
    NOTE as "비고",
    TO_CHAR(CREATED_DATE, 'YYYY-MM-DD HH24:MI:SS') as "생성일",
    TO_CHAR(UPDATED_DATE, 'YYYY-MM-DD HH24:MI:SS') as "수정일"
FROM EMP_ALLOWANCE 
WHERE COM_ID = 'com-101' 
ORDER BY EMP_ID, ALLOW_CODE;

-- 3. 급여항목 카테고리 7가지 확인
SELECT '급여항목 카테고리 7가지' as check_item FROM dual;
SELECT 
    MAIN_CODE as "메인코드",
    SUB_CODE as "서브코드", 
    SUB_NAME as "서브명",
    ACCT_YN as "사용여부",
    SORT_ORDER as "정렬순서",
    NOTE as "비고"
FROM SUBCODES 
WHERE MAIN_CODE = 'salarytype' AND COM_ID = 'com-101' 
ORDER BY SORT_ORDER;

-- 4. 사원 정보와 급여항목 연동 확인
SELECT '사원별 급여항목 연동 확인' as check_item FROM dual;
SELECT 
    e.EMP_ID as "사원번호",
    e.EMP_NAME as "사원명",
    e.DEPT_CODE as "부서코드",
    d.DEPT_NAME as "부서명",
    e.POSITION as "직급코드",
    e.SAL as "기본급",
    ea.ALLOW_CODE as "급여항목코드",
    ea.AMOUNT as "급여항목금액",
    ea.NOTE as "급여항목비고"
FROM EMPLOYEES e
LEFT JOIN DEPARTMENTS d ON e.DEPT_CODE = d.DEPT_CODE AND e.COM_ID = d.COM_ID
LEFT JOIN EMP_ALLOWANCE ea ON e.EMP_ID = ea.EMP_ID AND e.COM_ID = ea.COM_ID
WHERE e.COM_ID = 'com-101'
ORDER BY e.EMP_ID, ea.ALLOW_CODE;

-- 5. 급여명세서 데이터 확인
SELECT '급여명세서 데이터' as check_item FROM dual;
SELECT 
    SAL_ID as "급여명세서ID",
    EMP_ID as "사원번호",
    CALC_MONTH as "계산년월",
    BASE_SAL as "기본급",
    BONUS as "상여금",
    MEAL_ALLOW as "식대수당",
    NET_SALARY as "실수령액",
    STATUS as "상태",
    TO_CHAR(CREATED_DATE, 'YYYY-MM-DD HH24:MI:SS') as "생성일"
FROM SALARYSTATEMENT 
WHERE COM_ID = 'com-101'
ORDER BY CALC_MONTH DESC, EMP_ID;

-- 6. 급여항목 마스터 데이터 확인
SELECT '급여항목 마스터 데이터' as check_item FROM dual;
SELECT 
    ALLOW_CODE as "항목코드",
    ALLOW_NAME as "항목명",
    TYPE as "카테고리",
    DEFAULT_AMOUNT as "기본금액",
    CALC_TYPE as "계산방식",
    ACCT_YN as "사용여부",
    WORK_TYPES as "적용대상",
    NOTE as "비고"
FROM SALARYITEM 
WHERE COM_ID = 'com-101'
ORDER BY TYPE, ALLOW_CODE;

-- 7. 사원별 급여항목 통계
SELECT '사원별 급여항목 통계' as check_item FROM dual;
SELECT 
    EMP_ID as "사원번호",
    COUNT(*) as "급여항목수",
    SUM(AMOUNT) as "총급여항목금액",
    AVG(AMOUNT) as "평균급여항목금액"
FROM EMP_ALLOWANCE 
WHERE COM_ID = 'com-101'
GROUP BY EMP_ID
ORDER BY EMP_ID;

-- 8. 급여항목별 통계
SELECT '급여항목별 통계' as check_item FROM dual;
SELECT 
    ALLOW_CODE as "급여항목코드",
    COUNT(*) as "적용사원수",
    SUM(AMOUNT) as "총금액",
    AVG(AMOUNT) as "평균금액",
    MIN(AMOUNT) as "최소금액",
    MAX(AMOUNT) as "최대금액"
FROM EMP_ALLOWANCE 
WHERE COM_ID = 'com-101'
GROUP BY ALLOW_CODE
ORDER BY ALLOW_CODE;

-- 9. 데이터 무결성 검사
SELECT '데이터 무결성 검사' as check_item FROM dual;

-- 9-1. EMP_ALLOWANCE에 있지만 EMPLOYEES에 없는 사원
SELECT 'EMP_ALLOWANCE에 있지만 EMPLOYEES에 없는 사원' as check_item FROM dual;
SELECT DISTINCT ea.EMP_ID
FROM EMP_ALLOWANCE ea
LEFT JOIN EMPLOYEES e ON ea.EMP_ID = e.EMP_ID AND ea.COM_ID = e.COM_ID
WHERE e.EMP_ID IS NULL AND ea.COM_ID = 'com-101';

-- 9-2. EMP_ALLOWANCE에 있지만 SALARYITEM에 없는 급여항목
SELECT 'EMP_ALLOWANCE에 있지만 SALARYITEM에 없는 급여항목' as check_item FROM dual;
SELECT DISTINCT ea.ALLOW_CODE
FROM EMP_ALLOWANCE ea
LEFT JOIN SALARYITEM si ON ea.ALLOW_CODE = si.ALLOW_CODE AND ea.COM_ID = si.COM_ID
WHERE si.ALLOW_CODE IS NULL AND ea.COM_ID = 'com-101';

-- 10. 샘플 데이터 삽입 (테스트용)
SELECT '샘플 데이터 삽입' as check_item FROM dual;

-- 10-1. 새로운 사원별 급여항목 추가
INSERT INTO EMP_ALLOWANCE (EMP_ID, COM_ID, ALLOW_CODE, AMOUNT, NOTE, CREATED_DATE, UPDATED_DATE) 
VALUES ('e1006', 'com-101', 'MEAL', 250000, '식대 지원 (신규)', SYSDATE, SYSDATE);

INSERT INTO EMP_ALLOWANCE (EMP_ID, COM_ID, ALLOW_CODE, AMOUNT, NOTE, CREATED_DATE, UPDATED_DATE) 
VALUES ('e1006', 'com-101', 'TRANSPORT', 120000, '교통비 지원 (신규)', SYSDATE, SYSDATE);

INSERT INTO EMP_ALLOWANCE (EMP_ID, COM_ID, ALLOW_CODE, AMOUNT, NOTE, CREATED_DATE, UPDATED_DATE) 
VALUES ('e1006', 'com-101', 'COMMUNICATION', 50000, '통신비 지원', SYSDATE, SYSDATE);

COMMIT;

-- 11. 최종 확인
SELECT '최종 확인 - 전체 사원별 급여항목' as check_item FROM dual;
SELECT 
    e.EMP_NAME as "사원명",
    ea.ALLOW_CODE as "급여항목",
    ea.AMOUNT as "금액",
    ea.NOTE as "비고"
FROM EMP_ALLOWANCE ea
LEFT JOIN EMPLOYEES e ON ea.EMP_ID = e.EMP_ID AND ea.COM_ID = e.COM_ID
WHERE ea.COM_ID = 'com-101'
ORDER BY e.EMP_NAME, ea.ALLOW_CODE;

-- 12. 완료 메시지
SELECT '✅ DB 데이터 정상 저장/조회 확인 완료!' as result FROM dual; 