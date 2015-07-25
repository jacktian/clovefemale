$(function() {
  /* 加载胎动数据，从服务器获取数据 */
  var pregmData, pregmLabel;
  loadMovementFromServer = function() {
    //加载表格数据
    $.get('/CMove/findMovement', function(data) {
      var html = juicer($('#pregmTpl').html(), {
        pregmList: data.data
      });
      $('#pregmTable tbody').html(html);
      if (data.data.length == 0) {
        $('.pregmTableArrow').css("display", "none");
        var emptyHtml = "<tr><td>暂无数据</td><td>暂无数据</td></tr>";
        $('#pregmTable tbody').html(emptyHtml);
      } else if (data.data.length <= 4) {
        $('.pregmTableArrow').css("display", "none");
        $('#lastPregmViewBtn').html("上一次(" + data.data[data.data.length - 1].dateStr + "):" + data.data[data.data.length - 1].num + "次");
      } else {
        $('.pregwTableArrow').css("display", "block");
        $('#lastPregmViewBtn').html("上一次(" + data.data[data.data.length - 1].dateStr + "):" + data.data[data.data.length - 1].num + "次");
      }
    });
    //加载图表数据
    $.get('/CMove/lastMovementChart', function(data) {
      pregmLabel = data.data.label;
      pregmMor = data.data.morning;
      pregmAfter = data.data.afternoon;
      pregmEven = data.data.evening;
      var loadMoveChart = function() {
        // 基于准备好的dom，初始化echarts图表
        $('#pregmChart').css("width", $(window).get(0).innerWidth * 0.94);
        var myChart = echarts.init(document.getElementById('pregmChart'));

        var option = {

          tooltip: {
            show: true,
          },
          legend: {
            x: 'center',
            data: ['早上', '下午', '晚上']
          },
          title: {
            subtext: '近7日胎动变化'
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
            data: pregmLabel
          }],
          yAxis: [{
            type: 'value',
            min: 0,
            max: 45,
            axisLabel: {
              formatter: '{value} 次'
            }
          }],
          series: [{
            "name": "早上",
            "type": "line",
            "data": pregmMor,
            smooth: true,
            showAllSymbol: true
          }, {
            "name": "下午",
            "type": "line",
            "data": pregmAfter,
            smooth: true,
            showAllSymbol: true
          }, {
            "name": "晚上",
            "type": "line",
            "data": pregmEven,
            smooth: true,
            showAllSymbol: true
          }]
        };
        // 为echarts对象加载数据
        myChart.setOption(option);
      };

      if (pregmMor.length == 0) {
        $('.pregm-chart-empty').html("图表无数据显示");
        $('.pregm-chart-empty').removeClass('not-shown');
        $('#pregmChart').addClass('hidden');
      } else {
        $('#pregmChart').removeClass('hidden');
        $('.pregm-chart-empty').addClass('not-shown');
        loadMoveChart();
      }
    })
  };

  /* 胎动数据提交与修改*/
  $('.pregm-data-refresh').bind('touchend',
    function() {
      var date = $("#pregm-date-val").attr("data-val");
      var pregm = $('#pregm-scroller').mobiscroll('getVal');
      var movValue = $('.moveRadio input[name="movement"]:checked').val();
      if (pregm == null || typeof(pregm) == undefined)
        pregm = "0";
      $.get('/CMove/addMovement?date=' + date + "&pregm=" + pregm + "&time=" + movValue,
        function(data) {
          if (data.result == 0) {
            mui.toast('提交成功');
            loadMovementFromServer();
            $('#pregDrop').hide();
            $('.form-model').addClass('mui-hidden');
          } else if (data.data == "dateExp") {
            mui.toast('请选择正确的时间');
          } else {
            mui.toast('提交失败');
          }
        });
    });
  /* 胎动数据删除 */
  $('.pregm-data-delete').bind('touchend', function() {
    var date = $('#pregm-date-val').attr('data-val');
    $.get('/CMove/removeMovement?date=' + date, function(data) {
      if (data.result == 0) {
        mui.toast('删除成功');
        $('.pregm-data-delete').removeClass('deleted');
        $('.pregm-data-delete').attr('disabled', true);
        loadMovementFromServer();
        $('#pregDrop').hide();
        $('.form-model').addClass('mui-hidden');
      } else {
        mui.toast('请选择正确的日期');
      }
    });
  })
});
