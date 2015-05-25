package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;

import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;

/**
 * 宝贝表
 *
 * @author boxizen
 * @since 12/16/14
 */

@Entity
@Table(name = "baby")
public class Baby extends BasicModel{
	
	/**
     * 父母ID
     */
	@Column(name = "pid")
	public String userId;
	
	/**
     * 出生日期
     */
	@Column(name = "date")
	public Date date;
	
	/**
	 * 日期字符串
	 */
	@Required
	@Column(name = "dateStr")
	public String dateStr;

	/**
	 *头像路径 
	 */
	@Column(name = "headImgUrl")
	public String headImgUrl;
	
	/**
     * 性别
     */
	@Column(name = "sex")
	public String sex;

	/**
     * 姓名
     */
	@Column(name = "name")
	public String name;
	
	public Baby(){};
	
	public Baby(String id,String userId,Date date,String dateStr,String headImgUrl, String sex,String name){
		this.id = id;
		this.userId = userId;
		this.date = date;
		this.dateStr = dateStr;
		this.headImgUrl = headImgUrl;
		this.sex = sex;
		this.name = name;
	}
}