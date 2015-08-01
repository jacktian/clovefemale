$(function() {
  /* 加载月经记录，从服务器获取数据，更新上一条记录与图表信息 */
  var menseData, menseLabel;
  loadMenseFromServer = function() {
    //图表渲染
    $.get('/CPregnant/renderMense',
      function(data) {
        /*var ctx = $("#mense-chart").get(0).getContext("2d");
        ctx.clearRect(0, 0, $(window).get(0).innerWidth, 150);
        menseData = data.data.data;
        menseLabel = data.data.label;
        if (menseData.length == 0) {
          $('.mense-chart-empty').html("图表无数据显示");
          $('.mense-chart-empty').removeClass('not-shown');
        } else if (menseData.length == 1) {
          $('.mense-chart-empty').html("日期: " + data.data.label[0] + ", 周期: " + data.data.data[0] + "天");
          $('.mense-chart-empty').removeClass('not-shown');
        } else {
          $('.mense-chart-empty').addClass('not-shown');
          var loadMenseChart = function() {
            $(".preg-chart").attr("width", $(window).get(0).innerWidth);
            $(".preg-chart").attr("height", "150");

            var options = {
              animation: false
            };
            var data = {
              labels: menseLabel,
              datasets: [{
                fillColor: "rgba(242,195,195,0.4)",
                strokeColor: "rgba(223,116,116,0.4)",
                pointColor: "rgba(223,116,116,0.4)",
                pointStrokeColor: "#fff",
                data: menseData
              }]
            }
            new Chart(ctx).Line(data, options);
          };
          loadMenseChart();
        }*/
        console.log(data);
        pregData = data.data.data;
                pregLabel = data.data.label;

                var loadMenseChart = function() {
                  // 基于准备好的dom，初始化echarts图表
                  $('#menseChart').css("width", $(window).get(0).innerWidth * 0.94);
                  var myChart = echarts.init(document.getElementById('menseChart'));

                  var option = {
                    color: ['#E79996'],
                    tooltip: {
                      show: true,
                    },
                    legend: {
                      x: 'center',
                      data: ['经期']
                    },
                    title: {
                      subtext: '经期图表'
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
                      data: pregLabel
                    }],
                    yAxis: [{
                      type: 'value',
                      /*min: 0,
                      max: 100,*/
                      axisLabel: {
                        formatter: '{value} 天'
                      }
                    }],
                    series: [{
                      "name": "经期",
                      "type": "line",
                      "data": pregData,
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


                if (pregData.length == 0) {
                  $('.mense-chart-empty').html("图表无数据显示");
                  $('.mense-chart-empty').removeClass('not-shown');
                  $('#menseChart').addClass('hidden');
                } else {
                  $('#menseChart').removeClass('hidden');
                  $('.mense-chart-empty').addClass('not-shown');
                  loadMenseChart();
                }


      });
    //上一次记录渲染
    $.get('/CPregnant/lastMense',
      function(data) {
        if (data.data != "empty") {
          var color, num, liquid, hurt, chou, date, time;
          $('#lastMenseColor').text(data.data[0]);
          color = data.data[0];
          $('#lastMenseNum').text(data.data[1]);
          num = data.data[1];
           $('#lastMenseHurt').text(data.data[3]);
           hurt = data.data[3];
          date = data.data[4];
           $('#lastMenseLiquid').text(data.data[2]);
                      liquid = data.data[2];

          $('.mense-last-date').text(" (" + data.data[4] + ")");
          $('.model-last-mense-data').text("上一次(" + date + "):" + color + "," + num + "," + liquid + "," + hurt);
        } else {
          $('#lastMenseColor').text("无记录");
          $('#lastMenseNum').text("无记录");
          $('#lastMenseLiquid').text("无记录");
          $('#lastMenseHurt').text("无记录");
          $('.model-last-mense-data').text("暂无记录显示，请点击添加数据");
        }

      })
  }
  loadMenseFromServer();
  /* 加载月经图表 */
  loadMenseChart = function() {
    var ctx = $("#mense-chart").get(0).getContext("2d");
    ctx.clearRect(0, 0, $(window).get(0).innerWidth, 150);
    if (menseData.length == 0) {
      $('.mense-chart-empty').html("图表无数据显示");
      $('.mense-chart-empty').removeClass('not-shown');
    } else if (menseData.length == 1) {
      $('.mense-chart-empty').html("日期: " + menseLabel[0] + ", 周期: " + menseDate[0] + "天");
      $('.mense-chart-empty').removeClass('not-shown');
    } else {
      $('.mense-chart-empty').addClass('not-shown');
      var loadMenseChart = function() {
        $(".preg-chart").attr("width", $(window).get(0).innerWidth);
        $(".preg-chart").attr("height", "150");
        var options = {
          animation: false
        };
        var data = {
          labels: menseLabel,
          datasets: [{
            fillColor: "rgba(242,195,195,0.4)",
            strokeColor: "rgba(223,116,116,0.4)",
            pointColor: "rgba(223,116,116,0.4)",
            pointStrokeColor: "#fff",
            data: menseData
          }]
        }
        new Chart(ctx).Line(data, options);
      };
      loadMenseChart();
    }
  };
  /* 提交月经数据 */
  $('.mense-data-refresh').bind('touchend',
    function() {
      var menseColor = $("#mense-color").val();
      var menseHurt = $("#mense-hurt").val();
      var menseLiquid = $("#mense-liquid").val();
      var menseNum = $("#mense-num").val();
      console.log(menseHurt);
      var date = $("#mense-date-val").attr("data-val");
      console.log(menseColor+","+menseHurt+","+menseLiquid+","+menseNum+","+date);
      $.get('/CPregnant/addMenses?menseColor=' + menseColor+"&menseHurt=" + menseHurt + "&menseLiquid=" + menseLiquid + "&menseNum=" + menseNum + "&date=" + date,
        function(data) {
          if (data.result == 0) {
            mui.toast('保存成功');
            loadMenseFromServer();
            $('#pregDrop').hide();
            $('.form-model').addClass('mui-hidden');
          } else if (data.data == "dateExp") {
            mui.toast('请选择正确的时间');
          } else {
            mui.toast('保存失败');
          }
        });
    });
  /* 删除月经数据 */
  $('.mense-data-delete').bind('touchend', function() {
    var date = $("#mense-date-val").attr("data-val");
    if ($('.mense-data-delete').hasClass('deleted')) {
      $.get('/CPregnant/removeMense?date=' + date, function(data) {
        if (data.result == 0) {
          mui.toast('删除成功');
          $('.mense-data-delete').removeClass('deleted');
          $('.mense-data-delete').attr('disabled', true);
          loadMenseFromServer();
        } else {
          mui.toast('删除失败');
        }
      })
    }
  });
});
