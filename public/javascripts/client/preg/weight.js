$(function() {
  /* 加载孕重数据，从服务器获取数据 */
  var pregwData, pregwLabel;
  loadWeightFromServer = function() {
      //加载表格数据
      $.get('/CWeight/findWeight', function(data) {
        var html = juicer($('#pregwTpl').html(), {
          pregwList: data.data
        });
        $('#pregwTable tbody').html(html);
        if (data.data.length == 0) {
          $('.pregwTableArrow').css("display", "none");
          var emptyHtml = "<tr><td>暂无数据</td><td>暂无数据</td></tr>";
          $('#pregwTable tbody').html(emptyHtml);
        } else if (data.data.length <= 4) {
          $('.pregwTableArrow').css("display", "none");
          $('#lastPregwViewBtn').html("上一次(" + data.data[data.data.length - 1].dateStr + "):" + data.data[data.data.length - 1].weight + "Kg");
        } else {
          $('.pregwTableArrow').css("display", "block");
          $('#lastPregwViewBtn').html("上一次(" + data.data[data.data.length - 1].dateStr + "):" + data.data[data.data.length - 1].weight + "Kg");
        }
      });
      //加载图表数据
      $.get('/CWeight/lastWeightChart', function(data) {
        pregwData = data.data.data;
        pregwLabel = data.data.label;

        var loadWeightChart = function() {
          // 基于准备好的dom，初始化echarts图表
          $('#pregwChart').css("width", $(window).get(0).innerWidth * 0.94);
          var myChart = echarts.init(document.getElementById('pregwChart'));

          var option = {
            color: ['#E79996'],
            tooltip: {
              show: true,
            },
            legend: {
              x: 'center',
              data: ['孕重']
            },
            title: {
              subtext: '近7日体重变化'
            },
            toolbox: {
              show: true,
              feature: {
                restore: {
                  show: true
                },
                saveAsImage: {
                  show: false
                }
              }
            },
            grid: {
              x: 50,
              y: 60,
              x2: 50,
              y2: 50
            },
            xAxis: [{
              type: 'category',
              data: pregwLabel
            }],
            yAxis: [{
              type: 'value',
              min: 50,
              max: 66,
              axisLabel: {
                formatter: '{value} Kg'
              }
            }],
            series: [{
              "name": "孕重",
              "type": "line",
              "data": pregwData,
              smooth: true,
              showAllSymbol: true,
              markLine: {
                data: [{
                  type: 'average',
                  name: '平均值'
                }]
              }
            }]
          };
          // 为echarts对象加载数据
          myChart.setOption(option);
        };


        if (pregwData.length == 0) {
          $('.pregw-chart-empty').html("图表无数据显示");
          $('.pregw-chart-empty').removeClass('not-shown');
          $('#pregwChart').addClass('hidden');
        } else {
          $('#pregwChart').removeClass('hidden');
          $('.pregw-chart-empty').addClass('not-shown');
          loadWeightChart();
        }
      })
    }
    /* 孕重数据提交与修改*/
  $('.pregw-data-refresh').bind('touchend',
    function() {
      var date = $("#pregw-date-val").attr("data-val");
      var pregw = $('#pregw-scroller').mobiscroll('getVal');
      if (pregw == null || typeof(pregw) == undefined)
        pregw = "50 0";
      var fPregw = pregw.split(" ")[0] + "." + pregw.split(" ")[1];
      $.get('/CWeight/addWeight?date=' + date + "&pregw=" + fPregw,
        function(data) {
          if (data.result == 0) {
            mui.toast('提交成功');
            loadWeightFromServer();
            $('#pregDrop').hide();
            $('.form-model').addClass('mui-hidden');
          } else if (data.data == "dateExp") {
            mui.toast('请选择正确的日期');
          } else {
            mui.toast('提交失败');
          }
        });
    });
  /* 孕重数据删除 */
  $('.pregw-data-delete').bind('touchend', function() {
      var date = $('#pregw-date-val').attr('data-val');
      $.get('/CWeight/removeWeight?date=' + date, function(data) {
        if (data.result == 0) {
          mui.toast('删除成功');
          $('.pregw-data-delete').removeClass('deleted');
          $('.pregw-data-delete').attr('disabled', true);
          loadWeightFromServer();
          $('#pregDrop').hide();
          $('.form-model').addClass('mui-hidden');
        } else {
          mui.toast('请选择正确的日期');
        }
      });
    })
    /* 加载孕重图表 */
  loadPregwChart = function() {
    var ctx = $("#pregw-chart").get(0).getContext("2d");
    ctx.clearRect(0, 0, $(window).get(0).innerWidth, 150);
    if (pregwData.length == 0) {
      $('.pregw-chart-empty').html("图表无数据显示");
      $('.pregw-chart-empty').removeClass('not-shown');
    } else if (pregwData.length == 1) {
      $('.pregw-chart-empty').html("日期: " + pregwLabel[0] + ", 重量:" + pregwData[0]);
      $('.pregw-chart-empty').removeClass('not-shown');
    } else {
      $('.pregw-chart-empty').addClass('not-shown');
      $(".preg-chart").attr("width", $(window).get(0).innerWidth * .95);
      $(".preg-chart").attr("height", "150");
      var options = {
        animation: false
      };
      //This will get the first returned node in the jQuery collection.
      var data = {
        labels: pregwLabel,
        datasets: [{
          fillColor: "rgba(242,195,195,0.4)",
          strokeColor: "rgba(223,116,116,0.4)",
          pointColor: "rgba(223,116,116,0.4)",
          pointStrokeColor: "#fff",
          data: pregwData
        }]
      }
      var myNewChart = new Chart(ctx).Line(data, options);
    }
  };
});
