package beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 体重数据
 * @author boxizen
 * @since 2015/05/31
 */
public class PregwBean {
	/*
	 * 日期
	 */
	public Date date;
	
	/*
	 * 重量
	 */
	public float weight;
	
	/*
	 * 日期字符串 
	 */
	public String dateStr;
	
	/*
	 * 构造方法
	 */
	public PregwBean(Date date,float weight){
		this.date = date;
		this.weight = weight;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		dateStr = format.format(date);
	}
}
