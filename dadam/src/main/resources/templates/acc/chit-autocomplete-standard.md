# 스탠다드 전표관리 자동완성(Autocomplete) 기능 구조 정리

## 1. 프론트엔드

### (1) 자동완성 트리거 및 UI
- **파일:** `templates/standard/chit.html`
- **주요 코드:**
  ```js
  $('#chilInput').on('keyup', ev => {
    setupAutocomplete({
      inputSelector: '#chilInput',
      selectSelector: $('#chitSelect  option:selected').val(),
      listSelector: '#chitList',
      ajaxUrl: '/erp/standard/chitList'
    });
  });
  ```
- **설명:**
  - 사용자가 `#chilInput` 입력창에 값을 입력할 때마다 자동완성 함수가 실행됨
  - 자동완성 결과는 `#chitList`에 표시됨

### (2) 자동완성 공통 함수
- **파일:** `static/js/autoCompleteSearch.js`
- **함수:** `setupAutocomplete`
- **설명:**
  - 입력값이 2글자 이상일 때 서버에 AJAX 요청
  - 결과를 리스트에 표시, 클릭 시 입력창에 값 반영
  - 주요 파라미터: `inputSelector`, `selectSelector`, `listSelector`, `ajaxUrl`

---

## 2. 백엔드

### (1) REST 컨트롤러
- **파일:** `java/com/dadam/standard/item/web/ChitStandardRestController.java`
- **주요 메서드:**
  ```java
  @GetMapping("/standard/chitList")
  public List<ChitVO> chitList(@RequestParam Map<String,Object> map){
      List<ChitVO> result = service.chitList(map);
      return result;
  }
  ```
- **설명:**
  - 프론트엔드에서 AJAX로 호출되는 API

### (2) 서비스
- **파일:** `java/com/dadam/standard/item/service/impl/ChitStandardServiceImpl.java`
- **주요 메서드:**
  ```java
  public List<ChitVO> chitList(Map<String,Object> map){
      initAuthInfo();
      map.put("comId", comId);
      map.put("deptCode", deptCode);
      List<ChitVO> result =  mapper.ChitList(map);
      return result;
  }
  ```
- **설명:**
  - 인증정보에서 회사/부서코드 세팅 후 Mapper 호출

### (3) 매퍼 인터페이스
- **파일:** `java/com/dadam/standard/item/mapper/ChitStandardMapper.java`
- **주요 메서드:**
  ```java
  public List<ChitVO> ChitList(Map<String,Object> map);
  ```

### (4) MyBatis 매퍼(XML)
- **파일:** `resources/mapper/standard/chitStandrdMapper.xml`
- **주요 쿼리:**
  ```xml
  <select id="ChitList">
    SELECT CHIT_CODE, CHIT_TITLE, ... FROM chit
    WHERE com_id = #{comId}
    <!-- 부서별, 검색조건별 동적 where절 -->
    <choose>
      <when test="'chitCode'.equals(type)">
          AND chit_code LIKE '%'||#{value}||'%'
      </when>
      ...
    </choose>
    ...
    ORDER BY chit_code DESC
  </select>
  ```

---

## 3. 전체 연관 구조 요약

- **입력창**: `#chilInput` (chit.html)
- **자동완성 함수**: `setupAutocomplete` (autoCompleteSearch.js)
- **API**: `/erp/standard/chitList` (ChitStandardRestController.java)
- **서비스**: `chitList` (ChitStandardServiceImpl.java)
- **매퍼**: `ChitList` (ChitStandardMapper.java)
- **쿼리**: `ChitList` (chitStandrdMapper.xml)

---

### 구조도

```
#chilInput (chit.html)
  ↓ keyup
setupAutocomplete (autoCompleteSearch.js)
  ↓ ajax
/erp/standard/chitList (ChitStandardRestController.java)
  ↓
chitList (ChitStandardServiceImpl.java)
  ↓
ChitList (ChitStandardMapper.java)
  ↓
ChitList 쿼리 (chitStandrdMapper.xml)
```

---

이 파일은 스탠다드 전표관리 자동완성 기능의 전체 연관 구조와 파일, 함수, 흐름을 한눈에 파악할 수 있도록 정리한 문서입니다. 

---

## 4. 원하는 위치에서 자동검색(자동완성) 기능을 쓰고 싶다면?

### 1) HTML/JS에서 바꿔야 할 부분

- **inputSelector** : 자동완성을 적용할 입력창의 ID (예: `#myInput`)
- **selectSelector** : 검색 조건 셀렉트박스의 ID 또는 값 (예: `#mySelect option:selected`. 필요 없으면 직접 값 지정)
- **listSelector** : 자동완성 결과를 보여줄 리스트의 ID (예: `#myList`)
- **ajaxUrl** : 검색에 사용할 백엔드 API 주소 (예: `/erp/standard/chitList` 또는 원하는 API)

### 2) 실제 적용 예시

```html
<!-- 원하는 위치에 입력창, 셀렉트, 리스트 추가 -->
<input type="text" id="myInput" class="form-control">
<ul id="myList" class="list-group"></ul>
<select id="mySelect">
  <option value="chitCode">전표코드</option>
  <option value="chitTitle">전표명</option>
</select>
```

```js
// 원하는 입력창에 자동완성 기능 연결
$('#myInput').on('keyup', ev => {
  setupAutocomplete({
    inputSelector: '#myInput',
    selectSelector: $('#mySelect option:selected').val(),
    listSelector: '#myList',
    ajaxUrl: '/erp/standard/chitList' // 또는 원하는 API
  });
});
```

### 3) 백엔드(API)도 바꾸고 싶다면?
- **ajaxUrl**에 맞는 API가 이미 있다면 그대로 사용
- 새로운 검색 조건/필드를 추가하고 싶으면 컨트롤러, 서비스, 매퍼, 쿼리에서 해당 조건을 추가

---

**즉, input/select/list/ajaxUrl만 원하는 대로 바꿔주면 어디서든 자동검색 기능을 쉽게 붙일 수 있습니다!** 

---

## 5. 실제 적용 예시: acc/account.html에서 계정명 자동완성

### 1) HTML 예시
```html
<div class="input-group position-relative">
  <select class="form-select" id="acctSelect">
    <option value="ordCode">계정명</option>
    <option value="vdrCode">내용</option>
  </select>
  <input type="text" class="form-control" id="acctInput" placeholder="검색어 입력">
  <ul id="acctAutoList" class="list-group position-absolute w-100" style="z-index:1050; max-height:200px; overflow-y:auto; display:none;"></ul>
</div>
```

### 2) JS 예시
```js
$('#acctInput').on('keyup', function(ev) {
  setupAutocomplete({
    inputSelector: '#acctInput',
    selectSelector: $('#acctSelect option:selected').val(),
    listSelector: '#acctAutoList',
    ajaxUrl: '/erp/accounting/accountSearch'
  });
});
```

### 3) 설명
- 검색어를 입력하면 `/erp/accounting/accountSearch` API로 AJAX 요청을 보내고,
- 결과(계정명 등)를 자동완성 리스트(`acctAutoList`)로 보여줌
- 원하는 값을 클릭하면 입력창에 자동으로 반영됨

--- 