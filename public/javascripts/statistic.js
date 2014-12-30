$(function(){
	var tall = {
		labels : ["2012-01-01","2012-03-01","2012-04-01","2012-05-01","2012-06-01","2012-07-01","2012-08-01"],
		datasets : [
		{
			fillColor : "rgba(151,187,205,0.5)",
			strokeColor : "rgba(151,187,205,1)",
			pointColor : "rgba(151,187,205,1)",
			pointStrokeColor : "#fff",
			data : [151,160,162,163,167,172,174]
		}
		]
	}
	var temp = {
		labels : ["2012-01-01","2012-03-01","2012-04-01","2012-05-01","2012-06-01","2012-07-01","2012-08-01"],
		datasets : [
		{
			fillColor : "rgba(151,187,205,0.5)",
			strokeColor : "rgba(151,187,205,1)",
			pointColor : "rgba(151,187,205,1)",
			pointStrokeColor : "#fff",
			data : [36.5,37.5,37.2,38.6,39,36.2,37.7]
		}
		]
	}
	var score = {
		labels : ["January","February","March","April","May","June","July"],
		datasets : [
		{
			fillColor : "rgba(220,220,220,0.5)",
			strokeColor : "rgba(220,220,220,1)",
			data : [85,90,95,88,94,98,100]
		},
		{
			fillColor : "rgba(151,187,205,0.5)",
			strokeColor : "rgba(151,187,205,1)",
			data : [98,79,90,88,86,100,84]
		},
		{
			fillColor : "rgba(92,99,90,0.5)",
			strokeColor : "rgba(92,99,90,1)",
			data : [90,85,100,96,79,87,88]
		}
		]
	}
	var ctx = document.getElementById('myChart').getContext('2d');
	var statChart = new Chart(ctx);
	statChart.Line(tall);

	$(".topNav li").removeClass("active");
	$(".topNav li").eq(3).addClass("active");
	
	$(".statisticMenu li a").click(function(){
		$(".statisticMenu li").removeClass('active');
		$(this).parent().addClass('active');
	});

	$("#tallStat a").click(function(){
		ctx.clearRect(0,0,800,500);
		statChart.Line(tall);
	});

	$("#tempStat a").click(function(){
		ctx.clearRect(0,0,800,500);
		statChart.Line(temp);
	});

	$("#scoreStat a").click(function(){
		ctx.clearRect(0,0,800,500);
		statChart.Bar(score);
	})
});