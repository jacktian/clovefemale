package beans;

import java.util.Date;

public class NoteBean {
	public String id;
	public String title;
	public String content;
	public int year;
	public int month;
	public int day;
	public Date recentMFDate;
	public String noteBookId;
	public String noteBookName;
	public Date createDate;
	
	public NoteBean(String id,String title, String content, int year, int month, int day, Date recentMFDate, String noteBookId,String noteBookName){
		this.id = id;
		this.title = title;
		this.content = content;
		this.year = year;
		this.month = month;
		this.day = day;
		this.recentMFDate = recentMFDate;
		this.noteBookId = noteBookId;
		this.noteBookName = noteBookName;
	}
	
	
}
