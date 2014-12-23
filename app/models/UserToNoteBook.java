package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.collections.map.StaticBucketMap;

import com.sudocn.play.BasicModel;
/*
 * 用户与笔记本的关系类，一个用户可以有多个笔记本，一个笔记本只能属于一个用户，此类只存储用户和笔记本的主键Id。
 * */
@Entity
@Table(name = "user_notebook")
public class UserToNoteBook extends BasicModel{
    @Column(name = "user_id")
	public String userId;//用户ID
    @Column(name = "notebook_id")
	public String noteBookId;//笔记本ID
	
	public UserToNoteBook(String userId,String noteBookId){
		this.userId=userId;
		this.noteBookId=noteBookId;
	}
	/*
	 * 功能：根据用户ID查找记录
	 * 参数：用户ID
	 * */
	public static List<UserToNoteBook> findByUser(String userId){
		return find("userId = ?", userId).fetch();
	}
	/*
	 * 功能：根据笔记本ID查找记录
	 * 参数：笔记本ID
	 * */
	public static List<UserToNoteBook> findByNoteBook(String noteBookId){
		return find("noteBookId = ?", noteBookId).fetch();
	}
}
