package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Tanshichang
 * @since  2015-2-10
 */
public class StringUtils {

	
	/**
	 * 判断字符串是否为空
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s != null && !"null".equalsIgnoreCase(s) && s.length() > 0)
			return false;
		return true;
	}

	public static String formatDate_yyyy_MM_dd_HH_mm_ss(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ; 
		return sdf.format(date) ;
	}

	public static String formatDate_yyyy_MM_dd(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日") ;
		return sdf.format(date) ;
	}
	
	public static String formatDate_yyyy_MM_dd_HH_mm_ss_SSS(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS") ; 
		return sdf.format(date) ;
	}
	
}
