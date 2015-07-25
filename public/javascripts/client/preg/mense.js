$(function() {
  /* 加载月经记录，从服务器获取数据，更新上一条记录与图表信息 */
  var menseData, menseLabel;
  loadMenseFromServer = function() {
    //图表渲染
    $.get('/CPregnant/renderMense',
      function(data) {
        var ctx = $("#mense-chart").get(0).getContext("2d");
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
          if (data.data[2] == true) {
            $('#lastMenseLiquid').text("有");
            liquid = "有色块"
          } else {
            $('#lastMenseLiquid').text("无");
            liquid = "无色块"
          }
          if (data.data[3] == true) {
            $('#lastMenseHurt').text("有");
            hurt = "痛经";
          } else {
            $('#lastMenseHurt').text("无");
            hurt = "无痛";
          }
          $('#lastMenseChou').text(data.data[4]);
          chou = data.data[4];
          date = data.data[5];

          $('#lastMenseTime').text(data.data[6] + "天");
          time = "时长:" + data.data[6] + "天";

          $('.mense-last-date').text(" (" + data.data[5] + ")");
          $('.model-last-mense-data').text("上一次(" + date + "):" + color + "," + num + "," + liquid + "," + hurt + "," + chou + "," + time);
        } else {
          $('#lastMenseColor').text("无记录");
          $('#lastMenseNum').text("无记录");
          $('#lastMenseLiquid').text("无记录");
          $('#lastMenseHurt').text("无记录");
          $('#lastMenseChou').text("无记录");
          $('#lastMenseTime').text("无记录");
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
      var menseChou = $("#mense-chou").val();
      var menseHurt = $("#mense-hurt").val();
      var menseLiquid = $("#mense-liquid").val();
      var menseNum = $("#mense-num").val();
      var date = $("#mense-date-val").attr("data-val");
      var time = $('#mense-time').val();
      $.get('/CPregnant/addMenses?menseColor=' + menseColor + "&menseChou=" + menseChou + "&menseHurt=" + menseHurt + "&menseHurt=" + menseHurt + "&menseLiquid=" + menseLiquid + "&menseNum=" + menseNum + "&date=" + date + "&time=" + time,
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
