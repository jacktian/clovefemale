package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
/*
 * 笔记实体类，属性有Id、标题、内容、创建日期、最新修改日期时间、所属笔记本Id
 * 其中Id有系统自动创建，所属笔记本Id属外键，由另外的关系实体NoteBookToNote存储
 * */
@Entity
@Table(name = "note")
public class Note extends BasicModel{
    @Column(name = "title")
	public String title;//标题
    @Column(name = "content")
	public String content;//内容
    @Column(name = "createDate")
	public Date createDate;//创建日期
    @Column(name = "recentMFDate")
	public Date recentMFDate;//最新修改日期
    @Column(name = "noteBook_Id")
    public String noteBookId;
    /*
	 * 构造方法Note用于创建一条笔记记录，其中最新修改时间创建时初始化为创建时间
	 * */
    public Note(){}
	public Note(String title,String content,Date createDate,String noteBookId){
		this.title=title;
		this.content=content;
		this.createDate=createDate;
		this.recentMFDate=createDate;
		this.noteBookId=noteBookId;
	}
	
	/*
	 * 功能：当新增一张笔记记录时，建立笔记和笔记本的联系
	 * 参数：笔记ID，笔记本ID
	 * 
	 public static void createNtoN(String noteBookId,String noteId){
		 new NoteBookToNote(noteBookId, noteId).save();
	 }*/
}
