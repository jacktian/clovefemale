package beans;

import java.util.Date;

/**
 * 月经数据
 * @author boxizen
 * @since 2015/05/30
 */
public class MenseBean {
	/*
	 * 日期
	 */
	public Date mDate;
	
	/*
	 * 周期
	 */
	public String time;
	
	/*
	 * 构造方法
	 */
	public MenseBean(Date mDate,String time){
		this.mDate = mDate;
		this.time = time;
	}
}
