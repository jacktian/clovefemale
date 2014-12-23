package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
/*
 * 笔记本与笔记的关系类，一个笔记本可以有多个笔记，一个笔记只能属于一个笔记本，此类只存储笔记本和笔记的主键Id。
 * */
@Entity
@Table(name = "notebook_note")
public class NoteBookToNote extends BasicModel{
    @Column(name = "noteBook_id")
	public String noteBookId;//笔记本ID
    @Column(name = "note_id")
	public String noteId;//笔记ID
	
	public NoteBookToNote(String noteBookId,String noteId){
		this.noteBookId=noteBookId;
		this.noteId=noteId;
	}
	
	/*
	 * 功能：根据笔记本ID查找记录
	 * 参数：笔记本ID
	 * */
	public static List<NoteBookToNote> findByNoteBook(String noteBookId){
		return find("noteBookId = ?", noteBookId).fetch();
	}
	/*
	 * 功能：根据笔记ID查找记录
	 * 参数：笔记ID
	 * */
	public static List<NoteBookToNote> findByNote(String noteId){
		return find("noteId = ?", noteId).fetch();
	}
}
