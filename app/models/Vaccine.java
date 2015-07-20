package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;

/**
 *
 * @author cater Zhong
 * @since 6/7/15
 */
@Entity
@Table(name = "vaccine")
public class Vaccine extends BasicModel{
   
	/**
	 * 疫苗名称
	 */
	@Column(name = "name")
	public String name;
	
	/**
	 * 次数
	 */
	@Column(name = "time")
	public String time;
   
	/**
	 * 出生多少月后接种
	 */
	@Column(name = "monthafter")
	public int monthAfter;
	
	/**
	  * 是否需要间隔
	  */
	@Column(name = "hasinteral")
	public boolean hasInteral;
	
	/**
	  * 间隔的疫苗名称（一般为本身疫苗）
	  */
	@Column(name = "interalvacname")
	public String interalVacName;
	
	/**
	  * 月份间隔
	  */
	@Column(name = "monthinteral")
	public int monthInteral;
	   
	/**
	  * 天数间隔
	  */
	@Column(name = "dayinteral")
	public int dayInteral;
	
	/**
	 * 年龄描述
	 */
	@Column(name = "agedcb")
	public String ageDcb;
   
   /**
    * 可防的疾病
    */
   @Column(name = "pvdisease")
   public String pvDisease;
   
   public Vaccine(){}
   
	public Vaccine(String name,String time,int monthAfter,int monthInteral,int dayInteral,String ageDcb,String pvDisease){
		
		this.name = name;
		this.time = time;
		this.monthAfter = monthAfter;
		this.ageDcb = ageDcb;
		this.pvDisease = pvDisease;
	}
	
}
