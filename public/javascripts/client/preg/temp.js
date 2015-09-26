$(function() {
  /* 加载体温数据,从服务器端提取数据 */
  var tempData, tempLabel, sexData, sexObj;
  loadTempFromServer = function() {
    // 加载表格数据
    $.get('/CTemperature/lastTemp', function(data) {
        var html = juicer($('#tempTpl').html(), {
          tempList: data.data
        });
        $('#tempTable tbody').html(html);
        if (data.data.length == 0) {
          $('.tempTableArrow').css("display", "none");
          var emptyHtml = "<tr><td>暂无数据</td><td>暂无数据</td><td>暂无数据</td></tr>";
          $('#tempTable tbody').html(emptyHtml);
        } else if (data.data.length <= 4) {
          $('.tempTableArrow').css("display", "none");
          $('#lastTempViewBtn').html("上一次(" + data.data[data.data.length - 1].dateStr + "):" + data.data[data.data.length - 1].temp + "摄氏度");
        } else {
          $('.tempTableArrow').css("display", "block");
          $('#lastTempViewBtn').html("上一次(" + data.data[data.data.length - 1].dateStr + "):" + data.data[data.data.length - 1].temp + "摄氏度");
        }
      })
      //加载图表数据
    $.get('/CTemperature/lastTempChart', function(data) {
      tempData = data.data.data;
      tempLabel = data.data.label;
      sexData = data.data.sex;

        //获取数据的最大最小值作为图标的上下限

       var max_temp = -1;
       for(var i = 0; i < tempData.length; i++){
          if(max_temp < tempData[i]) max_temp = tempData[i];
       }
       var max_temp_int = Math.floor(max_temp) + 1;

      var loadTempChart = function() {
        // 基于准备好的dom，初始化echarts图表
        $('#tempChart').css("width", $(window).get(0).innerWidth * 0.94);
        var myChart = echarts.init(document.getElementById('tempChart'));

        var option = {
          color: ['#E79996'],
          tooltip: {
            show: true,
          },
          legend: {
            x: 'center',
            data: ['基础体温', '同房']
          },
          title: {
            subtext: '近7日体温变化'
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
            data: tempLabel/*,
            axisLabel:{
              interval: 0,
              rotate: -90
            }*/
          }],
          yAxis: [{
            type: 'value',
            min: 35.5,
            /*max: 37.5,*/
            max: max_temp_int,
            axisLabel: {
              formatter: '{value} °C'
            }
          }],
          series: [{
            "name": "基础体温",
            "type": "line",
            "data": tempData,
            smooth: true,
            showAllSymbol: true,
            markLine: {
              data: [{
                type: 'average',
                name: '平均值'
              }]
            }
          }, {
            "name": "同房",
            "type": "scatter",
            "data": sexData,
            smooth: true,
            showAllSymbol: true,
            markPoint: {
              symbolSize: 5,
              data: sexData
            },
          }]
        };
        // 为echarts对象加载数据
        myChart.setOption(option);
      };

      if (tempData.length == 0) {
        $('.temp-chart-empty').html("图表无数据显示");
        $('.temp-chart-empty').removeClass('not-shown');
        $('#tempChart').addClass('hidden');
      } else {
        $('#tempChart').removeClass('hidden');
        $('.temp-chart-empty').addClass('not-shown');
        loadTempChart();
      }
    });
  };

  /* 体温数据提交与修改*/
  $('.temp-data-refresh').bind('touchend',
    function() {
      var sexValue = $('.sexRadio input[name="haveSex"]:checked').val();
      var date = $("#temp-date-val").attr("data-val");
      var temp = $('#temp-scroller').mobiscroll('getVal');
      if (temp == null || typeof(temp) == undefined)
        temp = "35 0";
      var fTemp = temp.split(" ")[0] + "." + temp.split(" ")[1];
      $.get('/CTemperature/addTemperature?date=' + date + "&temp=" + fTemp + "&sex=" + sexValue,
        function(data) {
          if (data.result == 0) {
            mui.toast('提交成功');
            loadTempFromServer();
            $('#pregDrop').hide();
            $('.form-model').addClass('mui-hidden');
          } else if (data.data == "dateExp") {
            mui.toast('请选择正确的日期');
          } else {
            mui.toast('提交失败');
          }
        });
    });

  /* 体温数据删除 */
  $('.temp-data-delete').bind('touchend', function() {
    var date = $('#temp-date-val').attr('data-val');
    $.get('/CTemperature/removeTemp?date=' + date, function(data) {
      if (data.result == 0) {
        mui.toast('删除成功');
        $('.temp-data-delete').removeClass('deleted');
        $('.temp-data-delete').attr('disabled', true);
        loadTempFromServer();
        $('#pregDrop').hide();
        $('.form-model').addClass('mui-hidden');
      } else {
        mui.toast('请选择正确的日期');
      }
    });
  })
})
