package beans;

import models.FetalMovement;
import models.GestationalWeight;
import models.GradeForm;
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
     * 体重对象
     */
	public GestationalWeight weight;
	
	/**
     * 胎动对象
     */
	public FetalMovement condition;
}
