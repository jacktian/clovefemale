package beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 胎动数据
 * @author boxizen
 * @since 2015/05/31
 */
public class PregmBean {
	/*
	 * 日期
	 */
	public Date date;

	/*
	 * 次数
	 */
	public int num;

	/*
	 * 日期字符串
	 */
	public String dateStr;

	/*
	 * 纪录时间
	 */
	public int time;

	/*
	 * 构造方法
	 */
	public PregmBean(Date date,int num,int time){
		this.date = date;
		this.num = num;
		this.time = time;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		dateStr = format.format(date);
	}

	public PregmBean(Date date,int num){
		this.date = date;
		this.num = num;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		dateStr = format.format(date);
	}

	public PregmBean(){}
}
