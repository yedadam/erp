-- 급여항목 카테고리(유형) 7가지 공통코드 등록
-- MAIN_CODE: 'salarytype'

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

COMMIT;

-- 등록 확인
SELECT MAIN_CODE, SUB_CODE, SUB_NAME, ACCT_YN, SORT_ORDER, NOTE 
FROM SUBCODES 
WHERE MAIN_CODE = 'salarytype' AND COM_ID = 'com-101' 
ORDER BY SORT_ORDER; 