package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
/*
 * 孩子与疫苗接种的关系类，一个孩子有多条疫苗接种记录，一条疫苗记录对应一个孩子，主要属性是孩子ID和疫苗接种ID
 * */
@Entity
@Table(name = "baby_vacci")
public class BabyToVacci extends BasicModel{
    @Column(name = "baby_id")
	public String babyId;//孩子ID
    @Column(name = "vacci_id")
	public String vacciId;//疫苗接种ID
	
	public BabyToVacci(String babyId,String vacciId){
		this.babyId=babyId;
		this.vacciId=vacciId;
	}
	
	/*
	 * 功能：根据孩子ID查找记录
	 * 参数：孩子ID
	 * */
	public static List<BabyToVacci> findByBaby(String babyId){
		return find("babyId = ?", babyId).fetch();
	}
	/*
	 * 功能：根据疫苗接种ID查找记录
	 * 参数：疫苗接种ID
	 * */
	public static List<BabyToVacci> findByVacci(String vacciId){
		return find("vacciId = ?", vacciId).fetch();
	}
}
