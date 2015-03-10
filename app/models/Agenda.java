package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import utils.StringUtils;

import com.sudocn.play.BasicModel;

/**
 * 日程事件，用于定时提醒
 * @author Tanshichang
 * @since  2015-2-10
 */
@Entity
@Table(name = "agenda")
public class Agenda extends BasicModel{

	/** 日程事件类型：接种疫苗 */
	public static final int AGENDA_TYPE_VACCINATION = 1 ;
	/** 日程事件类型：药品过期 */
	public static final int AGENDA_TYPE_MEDECINE_STALE = 2 ;
	
	/** 用户Id */
	@Column(name = "user_id")
	public String userId;
	
	/** 日程事件类型 */
	@Column(name = "type")
	public int type ;
	
	/** 开始时间 */
	@Column(name = "start_time")
	public long startTime ;
	
	/** 结束时间 */
	@Column(name = "end_time")
	public long endTime ;
	
	/** 事件标题 */
	@Column(name = "title")
	public String title ;
	
	/** 事件描述 */
	@Column(name = "description")
	public String description ;
	
	/** 提前多少分钟提醒 */
	@Column(name = "remind_minutes")
	public int remindMinutes ;
	
	/** 事件发生地点 */
	@Column(name = "location")
	public String location ;

	@Override
	public String toString() {
		return "Agenda [userId=" + userId + ", type=" + type
				+ ", startTime=" + StringUtils.formatDate_yyyy_MM_dd_HH_mm_ss(new Date(startTime))
				+ ", endTime=" + StringUtils.formatDate_yyyy_MM_dd_HH_mm_ss(new Date(endTime)) 
				+ ", title=" + title
				+ ", description=" + description + ", remindMinutes="
				+ remindMinutes + ", location=" + location + ", id=" + id
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", willBeSaved=" + willBeSaved + "]";
	}

}
