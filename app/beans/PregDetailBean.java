package beans;

import models.GestationalWeight;
import models.GradeCondition;
import models.Menses;
import models.Temperature;

/**
 * 接口数据
 *
 * @author boxiZen
 * @since 3/14/15
 */
public class PregDetailBean {
	/**
     * 月经对象
     */
	public Menses mense;
	
	/**
     * 体温对象
     */
	public Temperature temp;
	
	/**
     * 孕重对象
     */
	public GestationalWeight weight;
	
	/**
     * 胎重对象
     */
	public GradeCondition condition;
}
