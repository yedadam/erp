/**
 *  autoCompleSearch.js
 */

 function setupAutocomplete({ inputSelector,        // 검색어 입력 input 
							     selectSelector,    // 검색 조건 select 
								 listSelector,      // 자동완성 결과 ul 
								 ajaxUrl}){         // 서버 요청 
	   	  
	      //제이쿼리에서 $(#vdrInput) <- 이런식으로 넣으면 HTML 요소를 찾아줌
	      //받을때도 $input 이런식으로 받는 이유는 제이쿼리 객체임을 명시 하기때문임
	      //제이쿼리 객체를 왜 명시하냐? 사실 명시 안해도됨 
	      //하지만 명시를 하면 $input.on('click')           <- 이런식으로 해야하고
	      //명시를 안했다면 input.addEventListener('click') <- 이런형식으로 받아야함
		  const $input = $(inputSelector);
		  const $list = $(listSelector);
		  //두글자 이하일땐 return
		 
		    const keyword = $input.val().trim();
		    if (keyword.length < 2) {
		      $list.empty();
		      return;
		    }
			//option값의 선택된 값
		    const type = selectSelector;

		    $.ajax({
		      url: ajaxUrl,
		      method: 'GET',
		      data: {
		        type: type,
		        value: keyword
		      },
		      success: function (data) {
		        let html = '';

		        // 특수문자 이스케이프 처리 함수
			    // 정규식에서 특수문자는 특별한 의미를 가지는데
			    // 사용자가 입력한 키워드에 특수문자가 있으면 의도하지 않은 동작이 발생할 수 있음.
			    // 그래서 특수문자 앞에 역슬래시를 붙여 그냥 문자임을 알려줌.
		        const escapeRegex = str => str.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
		        const safeKeyword = escapeRegex(keyword);
		     	// 이스케이프 처리한 키워드를 기반으로 정규식 패턴 생성
			    // g는 전체에서 모두 찾기 i는 대소문자 구분 안 함
			    // 예를 들어 책방이라는 키워드가 있으면 (책방)이라는 그룹 패턴을 만들어 모든 곳에서 대소문자 구분 없이 찾음
		        const keywordRegex = new RegExp(`(${safeKeyword})`, 'gi');
		      	//값이 없을때
		        if (data.length < 1) {
		          html += `<li class="list-group-item autocomplete-item no-result">
		                     <i class="fas fa-search-minus text-muted me-2"></i>조회된 값이 없습니다.
		                   </li>`;
		        }
		   	    
		        data.forEach(item => {
		        // type 문자열이 컬럼명이라 가정하고 item에서 해당 값을 꺼냄
		        let displayText = item[type] || '';  

		     	  // 정규식 그룹에 해당하는 부분을 찾아서 <span> 태그로 감싸 강조 표시
			      // $1은 정규식의 첫 번째 그룹에 해당하는 매칭 문자열을 의미
		        	  const highlighted = displayText.replace(keywordRegex, `<span class="text-danger fw-bold">$1</span>`);

		        	  html += `<li class="list-group-item autocomplete-item d-flex align-items-center" style="cursor: pointer;">
		        	             <i class="fas fa-search text-secondary me-2"></i> ${highlighted}
		        	           </li>`;
		        });

		        $list.html(html).show();
		      }
		    });
		

		  // 아이템 클릭 시 입력창에 텍스트 넣고 자동완성 닫기
		  $(document).on('click', `${listSelector} .autocomplete-item`, function () {
		    if ($(this).text().trim() === '조회된 값이 없습니다.') {
		      return;
		    }
		    $input.val($(this).text().trim());
		    $list.hide().empty();
		  });

		  // 바깥 클릭 시 자동완성 닫기
		  $(document).on('click', function (e) {
		    if (!$(e.target).closest(`${inputSelector}, ${listSelector}`).length) {
		      $list.hide().empty();
		    }
		  });
		} 
	
	//검색조건
	function commonSearch(type,value,url,grid){
			$.ajax({
				url : url,
				mehtod:'GET',
				data:{
					type:type,
					value:value
				},
				success:function(result){
					grid.resetData(result);
				}
			})
		}
		
		
		
		

		
		
		
	