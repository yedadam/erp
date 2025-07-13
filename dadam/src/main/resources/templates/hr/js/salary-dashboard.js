// 급여관리 대시보드 JavaScript

// 차트 초기화
function initCharts() {
    // 부서별 급여 분포 차트
    const departmentCtx = document.getElementById('departmentChart').getContext('2d');
    const departmentChart = new Chart(departmentCtx, {
        type: 'bar',
        data: {
            labels: ['영업부', '개발부', '관리부'],
            datasets: [{
                label: '급여액 (원)',
                data: [15000000, 20000000, 10000000],
                backgroundColor: [
                    'rgba(54, 162, 235, 0.5)',
                    'rgba(255, 99, 132, 0.5)',
                    'rgba(75, 192, 192, 0.5)'
                ],
                borderColor: [
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 99, 132, 1)',
                    'rgba(75, 192, 192, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function(value) {
                            return value.toLocaleString() + '원';
                        }
                    }
                }
            }
        }
    });

    // 직급별 급여 분포 차트
    const positionCtx = document.getElementById('positionChart').getContext('2d');
    const positionChart = new Chart(positionCtx, {
        type: 'pie',
        data: {
            labels: ['사원', '대리', '과장', '차장', '부장'],
            datasets: [{
                data: [20000000, 25000000, 30000000, 35000000, 40000000],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.5)',
                    'rgba(54, 162, 235, 0.5)',
                    'rgba(255, 206, 86, 0.5)',
                    'rgba(75, 192, 192, 0.5)',
                    'rgba(153, 102, 255, 0.5)'
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                }
            }
        }
    });
}

// 그리드 초기화
function initGrid() {
    const grid = new Grid({
        el: document.getElementById('payrollGrid'),
        columns: [
            { header: '사원명', name: 'empName', width: 120, align: 'center' },
            { header: '부서', name: 'deptName', width: 120, align: 'center' },
            { header: '직급', name: 'position', width: 100, align: 'center' },
            { header: '기본급', name: 'baseSalary', width: 100, align: 'right',
                formatter: ({value}) => value.toLocaleString() + '원'
            },
            { header: '수당', name: 'allowance', width: 90, align: 'right',
                formatter: ({row}) => row.allowance.toLocaleString() + '원'
            },
            { header: '공제액', name: 'deduction', width: 100, align: 'right',
                formatter: ({row}) => row.deduction.toLocaleString() + '원'
            },
            { header: '실지급액', name: 'netSalary', width: 120, align: 'right',
                formatter: ({value}) => value.toLocaleString() + '원'
            },
            { header: '지급일', name: 'payDate', width: 100, align: 'center' },
            { header: '상태', name: 'status', width: 100, align: 'center',
                formatter: ({value}) => {
                    const statusMap = {
                        'PAID': '지급완료',
                        'PENDING': '지급대기',
                        'ERROR': '에러'
                    };
                    return statusMap[value];
                }
            },
            { header: '관리', name: 'actions', width: 120, align: 'center',
                formatter: () => `
                    <button class="btn btn-sm btn-success" onclick="changeStatus('PAID')">
                        <i class="fas fa-check"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="changeStatus('ERROR')">
                        <i class="fas fa-exclamation-triangle"></i>
                    </button>
                `
            }
        ],
        rowHeaders: ['rowNum'],
        pageOptions: {
            useClient: true,
            perPage: 10
        }
    });

    return grid;
}

// 검증 함수
function validatePayroll(data) {
    if (!data.period) {
        Swal.fire({
            icon: 'warning',
            title: '필수 입력',
            text: '기간을 선택해주세요.',
            confirmButtonText: '확인'
        });
        return false;
    }
    if (!data.empName) {
        Swal.fire({
            icon: 'warning',
            title: '필수 입력',
            text: '사원명을 입력해주세요.',
            confirmButtonText: '확인'
        });
        return false;
    }
    if (!data.baseSalary) {
        Swal.fire({
            icon: 'warning',
            title: '필수 입력',
            text: '기본급을 입력해주세요.',
            confirmButtonText: '확인'
        });
        return false;
    }
    if (data.baseSalary < 0) {
        Swal.fire({
            icon: 'warning',
            title: '입력 오류',
            text: '기본급은 0원 이상이어야 합니다.',
            confirmButtonText: '확인'
        });
        return false;
    }
    return true;
}

// API 호출 함수
async function callApi(url, method, data) {
    try {
        showLoading();
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': getCsrfToken()
            },
            body: data ? JSON.stringify(data) : null
        });
        hideLoading();

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        hideLoading();
        Swal.fire({
            icon: 'error',
            title: 'API 오류',
            text: error.message,
            confirmButtonText: '확인'
        });
        throw error;
    }
}

// 상태 변경 함수
async function changeStatus(newStatus) {
    const selectedRow = grid.getFocusedCell();
    if (!selectedRow.rowKey) {
        Swal.fire({
            icon: 'warning',
            title: '선택 오류',
            text: '변경할 행을 선택해주세요.',
            confirmButtonText: '확인'
        });
        return;
    }

    const data = grid.getRowData(selectedRow.rowKey);
    const result = await callApi('/api/payroll/status', 'PUT', {
        id: data.id,
        status: newStatus
    });

    if (result.success) {
        grid.setData(result.data);
        Swal.fire({
            icon: 'success',
            title: '성공',
            text: '상태가 변경되었습니다.',
            confirmButtonText: '확인'
        });
    }
}

// 데이터 로드 함수
async function loadData() {
    const result = await callApi('/api/payroll/list', 'GET');
    if (result.success) {
        grid.setData(result.data);
    }
}

// 초기화 함수
document.addEventListener('DOMContentLoaded', () => {
    initCharts();
    const grid = initGrid();
    loadData();

    // 검색 이벤트
    document.getElementById('searchInput').addEventListener('keyup', (e) => {
        if (e.key === 'Enter') {
            loadData();
        }
    });

    // 필터 변경 이벤트
    document.querySelectorAll('select').forEach(select => {
        select.addEventListener('change', loadData);
    });

    // 급여 생성 이벤트
    document.getElementById('createPayroll').addEventListener('click', async () => {
        const form = document.getElementById('payrollForm');
        const formData = new FormData(form);
        const data = {};
        formData.forEach((value, key) => {
            data[key] = value;
        });

        if (validatePayroll(data)) {
            const result = await callApi('/api/payroll', 'POST', data);
            if (result.success) {
                form.reset();
                loadData();
                Swal.fire({
                    icon: 'success',
                    title: '성공',
                    text: '급여가 생성되었습니다.',
                    confirmButtonText: '확인'
                });
            }
        }
    });

    // 엑셀 다운로드 이벤트
    document.getElementById('exportExcel').addEventListener('click', async () => {
        const result = await callApi('/api/payroll/excel', 'GET');
        if (result.success) {
            window.location.href = result.data;
        }
    });
});

// CSRF 토큰 가져오기
function getCsrfToken() {
    const meta = document.querySelector('meta[name="csrf-token"]');
    return meta ? meta.getAttribute('content') : '';
}

// 로딩 표시
function showLoading() {
    const loadingIndicator = document.createElement('div');
    loadingIndicator.className = 'loading';
    loadingIndicator.innerHTML = '<div class="spinner-border text-primary" role="status"></div>';
    document.body.appendChild(loadingIndicator);
}

// 로딩 숨기기
function hideLoading() {
    const loading = document.querySelector('.loading');
    if (loading) {
        loading.remove();
    }
}
