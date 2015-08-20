$(function() {
  /* 菜单事件 */
  $('.pregIcon').bind('touchend',
    function() {
      $('.pregIcon').removeClass('active');
      $(this).addClass('active');
      $('.preg-content').removeClass('active');
      $('#loading-btn').show();
      var tabNum = $(this).attr('tabNum');
      if (tabNum == "1") {
        $('.mense-content').addClass('active');
        curTab = 1;
      } else if (tabNum == "2") {
        $('.temp-content').addClass('active');
        if (tabTwo == false) {
          loadTempFromServer();
          tabTwo = true;
        }
        curTab = 2;
      } else if (tabNum == "3") {
        $('.pregw-content').addClass('active');
        if (tabThree == false) {
          loadWeightFromServer();
          tabThree = true;
        }
        curTab = 3;
      } else {
        $('.pregm-content').addClass('active');
        if (tabFour == false) {
          loadMovementFromServer();
          tabFour = true;
        }
        curTab = 4
      }
      $('#loading-btn').fadeOut(2000);
      return false;
    });

  /*遮罩层*/
  $('#pregDrop').bind('touchstart',
    function() {
      $(this).hide();
      $('.form-model').addClass('mui-hidden');
    });
  /* 弹出模态对话框 */
  $('#mense-add-btn').bind('touchend',
    function() {
      if (curTab == "1") {
        $('.mense-model').removeClass('mui-hidden');
        $('.mense-data-delete').removeClass('deleted');
        $('.mense-data-delete').attr('disabled', true);
      } else if (curTab == "2") {
        $('.temp-model').removeClass('mui-hidden');
        $('.temp-data-delete').removeClass('deleted');
        $('.temp-data-delete').attr('disabled', true);
      } else if (curTab == "3") {
        $('.pregw-model').removeClass('mui-hidden');
        $('.pregw-data-delete').removeClass('deleted');
        $('.pregw-data-delete').attr('disabled', true);
      } else {
        $('.pregm-model').removeClass('mui-hidden');
        $('.pregm-data-delete').removeClass('deleted');
        $('.pregm-data-delete').attr('disabled', true);
      }
      $('#pregDrop').show();
      return false;
    });

  /* mobiscroll 日期控件*/
  $('#mense-date-btn').mobiscroll().date({
    theme: 'mobiscroll',
    display: 'bottom',
    lang: 'zh',
    onSelect: function(valueText, inst) {
      var selectedDate = inst.getVal(); // Call the getVal method
      var date = $.mobiscroll.datetime.formatDate('yy-mm-dd', selectedDate);
      $('#mense-date-val').attr('data-val', date);
      $.get('/CPregnant/findMense?date=' + date, function(data) {
        if (data.result == 0) {
          $('.mense-data-delete').addClass('deleted');
          $('.mense-data-delete').attr('disabled', false);
        } else {
          $('.mense-data-delete').removeClass('deleted');
          $('.mense-data-delete').attr('disabled', true);
        }
      });
    }
  });
  $('#temp-date-btn').mobiscroll().date({
    theme: 'mobiscroll',
    display: 'bottom',
    lang: 'zh',
    onSelect: function(valueText, inst) {
      var selectedDate = inst.getVal(); // Call the getVal method
      var date = $.mobiscroll.datetime.formatDate('yy-mm-dd', selectedDate);
      $('#temp-date-val').attr('data-val', date);
      $.get('/CTemperature/findTemp?date=' + date, function(data) {
        if (data.result == 0) {
          $('.temp-data-delete').addClass('deleted');
          $('.temp-data-delete').attr('disabled', false);
        } else {
          $('.temp-data-delete').removeClass('deleted');
          $('.temp-data-delete').attr('disabled', true);
        }
      });
    }
  });
  $('#pregw-date-btn').mobiscroll().date({
    theme: 'mobiscroll',
    display: 'bottom',
    lang: 'zh',
    onSelect: function(valueText, inst) {
      var selectedDate = inst.getVal(); // Call the getVal method
      var date = $.mobiscroll.datetime.formatDate('yy-mm-dd', selectedDate);
      $('#pregw-date-val').attr('data-val', date);
      $.get('/CWeight/findWeightByDate?date=' + date, function(data) {
        if (data.result == 0) {
          $('.pregw-data-delete').addClass('deleted');
          $('.pregw-data-delete').attr('disabled', false);
        } else {
          $('.pregw-data-delete').removeClass('deleted');
          $('.pregw-data-delete').attr('disabled', true);
        }
      });
    }
  });
  $('#pregm-date-btn').mobiscroll().date({
    theme: 'mobiscroll',
    display: 'bottom',
    lang: 'zh',
    onSelect: function(valueText, inst) {
      var selectedDate = inst.getVal(); // Call the getVal method
      var date = $.mobiscroll.datetime.formatDate('yy-mm-dd', selectedDate);
      $('#pregm-date-val').attr('data-val', date);
      $.get('/CMove/findMove?date=' + date, function(data) {
        if (data.result == 0) {
          $('.pregm-data-delete').addClass('deleted');
          $('.pregm-data-delete').attr('disabled', false);
        } else {
          $('.pregm-data-delete').removeClass('deleted');
          $('.pregm-data-delete').attr('disabled', true);
        }
      });
    }
  });
  /* 表格事件*/
  $('.table-more-btn').bind('touchend',
      function() {
        if ($(this).hasClass("shrink")) {
          $('.table-more-icon').removeClass('mui-icon-arrowdown');
          $('.table-more-icon').addClass('mui-icon-arrowup');
          $('.table-more').removeClass('table-hidden');
          $(this).removeClass('shrink');
        } else {
          $('.table-more-icon').addClass('mui-icon-arrowdown');
          $('.table-more-icon').removeClass('mui-icon-arrowup');
          $('.table-more').addClass('table-hidden');
          $(this).addClass('shrink');
        }
        return false;
      })
    /* 滚轮控件 */
    /* 体温滚轮 */
  $('#temp-scroller').mobiscroll().scroller({
    theme: 'mobiscroll',
    display: 'inline',
    rows: 3,
    multiline: 2,
    //showLabel:true,
    layout: 'liquid',
    height: 40,
    wheels: [
      [{
        label: '整数部分',
        values: ["35", "36", "37", "38", "39", "40"]
      }, {
        label: '小数部分',
        values: ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"]
      }]
    ]
  });
  /* 孕重滚轮 */
  $('#pregw-scroller').mobiscroll().scroller({
    theme: 'mobiscroll',
    display: 'inline',
    rows: 3,
    multiline: 2,
    //showLabel:true,
    layout: 'liquid',
    height: 40,
    wheels: [
      [{
        label: '整数部分',
        values: ["50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65"]
      }, {
        label: '小数部分',
        values: ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"]
      }]
    ]
  });
  /* 胎动滚轮 */
  $('#pregm-scroller').mobiscroll().scroller({
    theme: 'mobiscroll',
    display: 'inline',
    rows: 3,
    showLabel: false,
    layout: 'liquid',
    height: 40,
    wheels: [
      [{
        label: '次数',
        values: ["0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45"]
      }]
    ]
  });
});
