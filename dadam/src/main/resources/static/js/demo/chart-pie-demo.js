// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

 // 페이지 로딩 시 차트 데이터 불러오기
  document.addEventListener("DOMContentLoaded", function () {
    fetchPieChartData();
  });

  function fetchPieChartData() {
    fetch('/erp/pieChart') // 이 URL은 @GetMapping("/erp/pieChart") 기준
      .then(response => response.json())
      .then(data => {
        const labels = data.map(row => row.itemName);
        const values = data.map(row => row.percentage);

        renderPieChart(labels, values);
      })
      .catch(error => console.error('도넛 차트 로딩 오류:', error));
  }

  function renderPieChart(labels, data) {
    const ctx = document.getElementById("myPieChart").getContext('2d');

    new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: labels,
        datasets: [{
          data: data,
          backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc', '#f6c23e', '#e74a3b', '#858796', '#20c9a6'],
          hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf', '#dda20a', '#be2617', '#6c757d', '#169c87'],
          hoverBorderColor: "rgba(234, 236, 244, 1)",
        }],
      },
      options: {
        maintainAspectRatio: false,
        tooltips: {
          backgroundColor: "rgb(255,255,255)",
          bodyFontColor: "#858796",
          borderColor: '#dddfeb',
          borderWidth: 1,
          xPadding: 15,
          yPadding: 15,
          displayColors: true,
          caretPadding: 10,
          callbacks: {
            label: function (tooltipItem, chart) {
              const dataset = chart.datasets[tooltipItem.datasetIndex];
              const currentValue = dataset.data[tooltipItem.index];
              const label = chart.labels[tooltipItem.index];
              return `${label}: ${currentValue}%`;
            }
          }
        },
        legend: {
          display: true,
          position: 'right'
        },
        cutoutPercentage: 80,
      },
    });
  }
