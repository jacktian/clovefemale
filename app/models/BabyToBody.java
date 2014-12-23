package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
/*
 * 孩子与身体指标的关系类，一个孩子有多条身体指标记录，一条身体指标记录只对应一个孩子，此类存储孩子和身体指标的主键ID
 * */
@Entity
@Table(name = "baby_body")
public class BabyToBody extends BasicModel{
    @Column(name = "baby_id")
	public String babyId;//孩子ID
    @Column(name = "body_id")
	public String bodyId;//身体指标ID
    
    public BabyToBody(String babyId,String bodyId){
    	this.babyId=babyId;
    	this.bodyId=bodyId;
    }
    
    /*
	 * 功能：根据宝宝ID查找记录
	 * 参数：宝宝ID
	 * */
	public static List<BabyToBody> findByBaby(String babyId){
		return find("babyId = ?", babyId).fetch();
	}
	/*
	 * 功能：根据身体指标ID查找记录
	 * 参数：身体指标ID
	 * */
	public static List<BabyToBody> findByBody(String bodyId){
		return find("bodyId = ?", bodyId).fetch();
	}
}
