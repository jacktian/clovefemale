package beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 体温数据
 * @author boxizen
 * @since 2015/05/30
 */
public class TempBean {
	/*
	 * 日期
	 */
	public Date date;

	/*
	 * 体温
	 */
	public float temp;

	/*
	 * 日期字符串
	 */
	public String dateStr;

	/*
	 * 同房
	 */
	public int sex;

	/*
	 * 构造方法
	 */
	public TempBean(Date date,float temp,int sex){
		this.date = date;
		this.temp = temp;
		this.sex = sex;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		dateStr = format.format(date);
	}

	public TempBean(Date date,float temp){
		this.date = date;
		this.temp = temp;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		dateStr = format.format(date);
	}
}
