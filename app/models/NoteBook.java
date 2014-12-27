package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
/*
 * 笔记本实体类，主要有Id、名称、创建日期时间、最新修改日期时间、创建人Id、简介属性
 * 其中Id有系统自动创建，创建人Id属外键，由另外的关系实体UserToNoteBook存储
 * */
@Entity
@Table(name = "notebook")
public class NoteBook extends BasicModel{
	@Column(name = "name")
	public String name;//名称
	@Column(name = "createDate")
	public Date createDate;//创建日期
	@Column(name = "recentMFDate")
	public Date recentMFDate;//最新修改日期
	@Column(name = "introduceContent")
	public String introduceContent;//简介
	@Column(name = "user_Id")
	public String userId;
	/*
	 * 构造方法noteBook用于创建笔记本，其中最新修改时间创建时初始化为创建时间
	 * */
	public NoteBook(){}
	public NoteBook(String name,Date createDate,String introduceContent,String userId){
		this.name=name;
		this.createDate=createDate;
		this.introduceContent=introduceContent;
		this.recentMFDate=createDate;
		this.userId= userId;
	}
	
	/*
	 * 功能：当新增一张笔记本记录时，建立用户和笔记本的联系
	 * 参数：用户ID，笔记本ID
	 * 
	 public static void createUtoN(String userId,String noteBookId){
		 new UserToNoteBook(userId, noteBookId).save();
	 }*/
}
