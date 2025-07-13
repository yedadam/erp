-- ========================================
-- ERP 급여항목 카테고리 7가지 + 사원별 급여항목 연동
-- 완전 자동화 설정 스크립트
-- ========================================

-- 1. EMP_ALLOWANCE 테이블 컬럼 추가
ALTER TABLE EMP_ALLOWANCE ADD NOTE VARCHAR2(200);
ALTER TABLE EMP_ALLOWANCE ADD CREATED_DATE DATE DEFAULT SYSDATE;
ALTER TABLE EMP_ALLOWANCE ADD UPDATED_DATE DATE DEFAULT SYSDATE;

-- 2. 인덱스 생성
CREATE INDEX IDX_EMP_ALLOWANCE_EMP ON EMP_ALLOWANCE(EMP_ID, COM_ID);
CREATE INDEX IDX_EMP_ALLOWANCE_CODE ON EMP_ALLOWANCE(ALLOW_CODE);

-- 3. 기존 데이터에 기본값 설정
UPDATE EMP_ALLOWANCE SET NOTE = '자동 등록' WHERE NOTE IS NULL;
UPDATE EMP_ALLOWANCE SET CREATED_DATE = SYSDATE WHERE CREATED_DATE IS NULL;
UPDATE EMP_ALLOWANCE SET UPDATED_DATE = SYSDATE WHERE UPDATED_DATE IS NULL;

-- 4. 급여항목 카테고리 7가지 공통코드 등록 (선택사항)
-- 기존 데이터 삭제 (중복 방지)
DELETE FROM SUBCODES WHERE MAIN_CODE = 'salarytype' AND COM_ID = 'com-101';

-- 7가지 실무 표준 급여항목 카테고리 등록
INSERT INTO SUBCODES (MAIN_CODE, SUB_CODE, SUB_NAME, COM_ID, ACCT_YN, SORT_ORDER, NOTE) VALUES
('salarytype', '기본급', '기본급', 'com-101', 'Y', 1, '사원의 기본 급여'),
('salarytype', '수당', '수당', 'com-101', 'Y', 2, '각종 수당 (연장, 야간, 휴일 등)'),
('salarytype', '복리후생', '복리후생', 'com-101', 'Y', 3, '식대, 교통비, 통신비 등'),
('salarytype', '상여', '상여', 'com-101', 'Y', 4, '성과상여, 특별상여 등'),
('salarytype', '공제', '공제', 'com-101', 'Y', 5, '4대보험, 소득세, 지방세 등'),
('salarytype', '비과세', '비과세', 'com-101', 'Y', 6, '비과세 수당'),
('salarytype', '기타', '기타', 'com-101', 'Y', 7, '기타 급여항목');

-- 5. 샘플 사원별 급여항목 데이터 추가
INSERT INTO EMP_ALLOWANCE (EMP_ID, COM_ID, ALLOW_CODE, AMOUNT, NOTE) VALUES
('e1001', 'com-101', 'MEAL', 200000, '식대 지원'),
('e1001', 'com-101', 'TRANSPORT', 100000, '교통비 지원'),
('e1002', 'com-101', 'MEAL', 200000, '식대 지원'),
('e1002', 'com-101', 'TRANSPORT', 100000, '교통비 지원'),
('e1003', 'com-101', 'MEAL', 200000, '식대 지원'),
('e1003', 'com-101', 'TRANSPORT', 100000, '교통비 지원'),
('e1004', 'com-101', 'MEAL', 200000, '식대 지원'),
('e1004', 'com-101', 'TRANSPORT', 100000, '교통비 지원'),
('e1005', 'com-101', 'MEAL', 200000, '식대 지원'),
('e1005', 'com-101', 'TRANSPORT', 100000, '교통비 지원');

COMMIT;

-- 6. 설정 확인 쿼리
SELECT 'EMP_ALLOWANCE 테이블 구조' as check_item FROM dual;
DESC EMP_ALLOWANCE;

SELECT '급여항목 카테고리 7가지' as check_item FROM dual;
SELECT MAIN_CODE, SUB_CODE, SUB_NAME, ACCT_YN, SORT_ORDER, NOTE 
FROM SUBCODES 
WHERE MAIN_CODE = 'salarytype' AND COM_ID = 'com-101' 
ORDER BY SORT_ORDER;

SELECT '사원별 급여항목 샘플 데이터' as check_item FROM dual;
SELECT * FROM EMP_ALLOWANCE WHERE COM_ID = 'com-101' ORDER BY EMP_ID, ALLOW_CODE;

-- 7. 완료 메시지
SELECT '✅ ERP 급여항목 카테고리 7가지 + 사원별 급여항목 연동 설정 완료!' as result FROM dual; 