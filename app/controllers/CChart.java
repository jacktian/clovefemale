package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import beans.ChartBean;
import play.db.jpa.JPA;
/**
 * 图表测试控制器
 * 
 * @author boxiZen
 * @since 2015/05/29
 */
public class CChart extends WebService{
	
	/**
	 * 渲染月经图表
	 */
	public static void renderMense(){
		ChartBean bean = new ChartBean();
		List labelList = new ArrayList();
		List dataList = new ArrayList();
		for(int i=0; i<7; i++){
			labelList.add("as");
			dataList.add(i);
		}
		bean.label = labelList;
		bean.data = dataList;
		wsOk(bean);
	}
}
