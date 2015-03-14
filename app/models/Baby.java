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
     * 性别
     */
	@Column(name = "sex")
	public String sex;

	/**
     * 姓名
     */
	@Column(name = "name")
	public String name;
}