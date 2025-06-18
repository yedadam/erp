    // collapse 메뉴가 열리고 닫힐 때 아이콘을 변경하는 코드
    $('#accordionSidebar').on('show.bs.collapse', function (e) {
            // 메뉴가 열릴 때 '>'에서 'v'로 바꾸기
            $(e.target).prev().find('i.fas').removeClass('fa-chevron-right').addClass('fa-chevron-down');
        });

        $('#accordionSidebar').on('hide.bs.collapse', function (e) {
            // 메뉴가 닫힐 때 'v'에서 '>'로 바꾸기
            $(e.target).prev().find('i.fas').removeClass('fa-chevron-down').addClass('fa-chevron-right');
        });