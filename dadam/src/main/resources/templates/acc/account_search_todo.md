/*
파일명: AccountRestController.java, AccountService.java, AccountServiceImpl.java, AccountMapper.java, AccountMapper.xml, AccountVO.java, account.html

추가/수정 함수:
- searchAccounts() : 계정명/내용/사용유무로 검색 (Controller, Service, Mapper)
- getAccountList() : Mapper, XML 쿼리
- (프론트) 검색 UI 및 JS 함수
*/

--- 아래는 함수별 샘플/설명 ---

// 1. Controller
// 계정명, 내용, 사용유무(라디오)로 계정 목록을 검색하는 API
@GetMapping("/account/search")
public List<AccountVO> searchAccounts(@RequestParam Map<String, Object> params) {
    return accountService.searchAccounts(params);
}
// params 예시: { "accountName": "", "description": "", "useYn": "Y|N|" }

// 2. Service
List<AccountVO> searchAccounts(Map<String, Object> params);

// 3. ServiceImpl
@Override
public List<AccountVO> searchAccounts(Map<String, Object> params) {
    return accountMapper.getAccountList(params);
}

// 4. Mapper (interface)
List<AccountVO> getAccountList(Map<String, Object> params);

// 5. Mapper.xml (MyBatis)
<select id="getAccountList" resultType="AccountVO">
  SELECT * FROM account
  WHERE 1=1
    <if test="accountName != null and accountName != ''">
      AND account_name LIKE '%'||#{accountName}||'%'
    </if>
    <if test="description != null and description != ''">
      AND description LIKE '%'||#{description}||'%'
    </if>
    <if test="useYn != null and useYn != ''">
      AND use_yn = #{useYn}
    </if>
</select>

// 6. VO (AccountVO.java)
private String accountName;
private String description;
private String useYn;
// ...기타 필드

// 7. 프론트 (account.html)
// - 계정명/내용 입력창, 사용유무 라디오버튼, 검색버튼 추가
// - JS에서 검색조건 수집 후 AJAX로 /account/search 호출, 결과 그리드에 표시
/*

// 계정과목 다중검색 (계정명, 내용, 사용유무)
$('#acctSearchBtn').on('click', function() {
  // 검색조건 수집
  const searchType = $('#acctSelect').val();
  const searchValue = $('#acctInput').val();
  const acctYn = $('input[name="acctStatus"]:checked').val();

  // 파라미터 구성
  let params = {};
  if (searchType === 'ordCode') {
    params.name = searchValue;
  } else if (searchType === 'vdrCode') {
    params.note = searchValue;
  }
  params.acctYn = acctYn;

  // 서버에 AJAX 요청
  $.ajax({
    url: '/erp/accounting/accountSearch',
    method: 'GET',
    data: params,
    success: function(result) {
      accGrid.resetData(result);
    },
    error: function(xhr, status, error) {
      alert('검색 실패: ' + error);
    }
  });
});
*/
/*
[적용 순서]
1. 프론트에 검색 UI 추가 (account.html)
2. Controller/Service/Mapper/VO/쿼리 함수 추가 및 연동
3. 프론트 JS에서 검색조건 받아 서버에 요청, 결과 표시
4. 테스트 및 개선
*/ 