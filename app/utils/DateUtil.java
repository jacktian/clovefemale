package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	  /**
	   * 
	   *计算某人的生日到当前一共有几个闰年，此方法是为了更精确考虑闰年，从而计算出生日。
	   *   * @param data 数据库传入的 生日 
	   */ 
	public static int getLY(Date date){ 
		int leapYear = 0;  
		if(date!=null) { 
			SimpleDateFormat myFormat = new SimpleDateFormat("yyyy");  
			Date now = new Date(); 
			int birthYear = Integer.parseInt(myFormat.format(date)); //获取出生日期，解析为Date类型  
			int currYear = Integer.parseInt(myFormat.format(now)); //获取当前日期   
			for(int year=birthYear;year<=currYear;year++) {     
				if((year%4==0 && year%100!=0)||(year%400==0)) {   
					leapYear++;//从出生年到当前年，只有是闰年就+1   
				}   
			}    
		}   
		return leapYear;   
	}  
	
	/**    *  
	 * 通过生日减去当前日期求年龄。
	 * @param data 接收数据库传来的birthDate：1980-08-09  
	 * @return 返回年龄   
	 */   
	public static int getAge(Date birthDate){   
		int age = 0;   
		long leapYear = (long)getLY(birthDate);//其实会自动转成int  
		if(birthDate!=null) { 
			SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			Date now = new Date(); 
			Date currDate;
			try{
				currDate = myFormat.parse(myFormat.format(now)); //获取当前日期   
			}catch(Exception e){
				currDate = now;
			}
			
			if(birthDate.getTime()<=currDate.getTime()) {
				//以来此 Date 对象表示的毫秒数     
				age = (int)(((currDate.getTime()-birthDate.getTime())/(24*60*60*1000)-leapYear)/365);   
				//365L表示长整型    
			}  
		} 
		return age;
	}
	 
	/**
	 * 日期加减
	 * date:原始日期
	 * day:加减的天数，正数为加，负数为减
	 */
	public static Date dateAdd(Date oldDate,int day){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar date = Calendar.getInstance();
		date.setTime(oldDate);
		date.add(Calendar.DAY_OF_YEAR, day);
		Date newDate = date.getTime();
		return newDate;
	}
}
