package controllers;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import models.Medicine;
import models.MedicineBox;
import models.Note;
import models.NoteBook;
import beans.MBean;
import beans.NBbean;
/**
 * 随手记controller
 * @author Yingpeng
 * @since 03/05/15
 * */
public class RandomNote extends WebService{

	public static void  findAllNoteBook(){
		List<NoteBook> list = NoteBook.findAll();
		wsOk(list);
	}
	
	public static void findAllNote(){
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		Map<String, String> map = new LinkedHashMap<String, String>() ;
		map.put("id", "0");
		map.put("title", "MVC结构");
		map.put("date", "2014-12-12");
		map.put("content", "从存储数据觉的逻辑角度上来说,一个数据仓库有点类似一个数据一览表，而一个记录有点");
		map.put("notebook", "菜谱");
		map.put("test", "test");
		list.add(map);
		
		map = new LinkedHashMap<String, String>() ;
		map.put("id", "1");
		map.put("title", "代理与阅读器");
		map.put("date", "2013-12-11");
		map.put("content", "表单面板组件Ext.form.Panel,别名Ext.form.FormPanel,xtype值为formpanel,表单");
		map.put("notebook", "孩子学习");
		map.put("test", "test");
		list.add(map);
		
		map = new LinkedHashMap<String, String>() ;
		map.put("id", "2");
		map.put("title", "数据仓库");
		map.put("date", "2014-12-1");
		map.put("content", "从存储数据觉的逻辑角度上来说,一个数据仓库有点类似一个数据一览表，而一个记录有点");
		map.put("notebook", "助孕");
		map.put("test", "test");
		list.add(map);
		
		map = new LinkedHashMap<String, String>() ;
		map.put("id", "3");
		map.put("title", "Xtype");
		map.put("date", "2012-11-11");
		map.put("content", "表单面板组件Ext.form.Panel,别名Ext.form.FormPanel,xtype值为formpanel,表单");
		map.put("notebook", "孩子学习");
		map.put("test", "test");
		list.add(map);
		
		map = new LinkedHashMap<String, String>() ;
		map.put("id", "4");
		map.put("title", "表单域");
		map.put("date", "2012-11-12");
		map.put("content", "从存储数据觉的逻辑角度上来说,一个数据仓库有点类似一个数据一览表，而一个记录有点");
		map.put("notebook", "菜谱");
		map.put("test", "test");
		list.add(map);
		wsOkAsExtJsonP(list);
	}
	
	/*
	 * 根据notebookid查询所有的身体指标
	 * 参数：noteBookId
	 * 返回：满足条件的列表
	 * */
	public static void findNoteByNotebook(String noteBookId){
		
		List<Note> notes = Note.find("noteBookId = ?", noteBookId).fetch();
		wsOk(notes);
	}
	
	
    public static void findNoteByNotebook2(String nbid,String nbname,int curpage){
		
    	if(curpage == 0){
	 		 curpage =1;
	 		}
		long count =0;
		long pageNum =0;
		List<Note> listbean = Note.find("noteBookId = ?", nbid).fetch(curpage,2);
		if(listbean!=null){
			count = Note.find("noteBookId = ?", nbid).fetch().size();
			if(count%2!=0)
	     		pageNum = count/2+1;
	     	else
	     		pageNum = count/2;
		}
		render("/NoteMgm/noteList.html",listbean,pageNum,curpage,nbid,nbname);
	}


	/*
	 * 根据userId查询所有的笔记本
	 * 参数：userId
	 * 返回：满足条件的列表
	 * */
	public static void findNoteBookByUser1(String userId){
		
		List<NoteBook> noteBooks = NoteBook.find("userId = ?", userId).fetch();
		wsOk(noteBooks);
	}
	
    public static void findNoteBookByUser(String userId,String userName,int curpage){
		
    	if(curpage == 0){
	 		 curpage =1;
	 		}
	 		long count =0;
	 		long pageNum =0;
	 		List<NBbean> listbean =null;
	 		List<NoteBook> list = NoteBook.find("userId = ?", userId).fetch(curpage,2);
	 		if(list!=null){
	 			count = NoteBook.find("userId = ?", userId).fetch().size();
	 			if(count%2!=0)
	 	     		pageNum = count/2+1;
	 	     	else
	 	     		pageNum = count/2;
	 			
	 			listbean=NBbean.builList(list);
	 		}
	 		
	 		render("/NoteMgm/nbList.html",listbean,pageNum,curpage,userName,userId);
	 		
	 		
	}

	
	public static List<NoteBook> findNoteBookByUser2(String userId){
		/*List<UserToNoteBook> list =UserToNoteBook.findByUser(userId);
		List<NoteBook> noteBooks = new ArrayList();
		for(UserToNoteBook userToNoteBook : list){
			NoteBook n= NoteBook.findById(userToNoteBook.noteBookId);
			noteBooks.add(n);
		}*/
		List<NoteBook> noteBooks = NoteBook.find("userId = ?", userId).fetch();
		return noteBooks;
	}
	/*
	 * 新增笔记本
	 * 参数：前台传回的实体类，用户id
	 * 返回：全部列表
	 * */
	public static void addNoteBook(NoteBook noteBook,String userId){
	  NoteBook n = noteBook.save();
	  /*NoteBook.createUtoN(userId, n.id);*/
	  findNoteBookByUser1(userId);
	}
	/*
	 * 新增笔记
	 * 参数：前台传回的实体类，笔记本id
	 * 返回：全部列表
	 * */
	public static void addNote(Note note,String noteBookId){
		Note n = note.save();
		/*Note.createNtoN(noteBookId, n.id);*/
		findNoteByNotebook(noteBookId);
	}
	/*
	 * 更新笔记本信息
	 * 参数：noteBook类，用户id
	 * 返回：全部列表
	 * */
	public static void updateNoteBook(NoteBook noteBook,String userId){
		noteBook.recentMFDate=new Date();
		noteBook.save();
		findNoteBookByUser1(userId);
	}
	/*
	 * 更新笔记信息
	 * 参数：notek类，笔记本id
	 * 返回：全部列表
	 * */
	
	public static void updateNote(Note note,String noteBookId){
		note.recentMFDate=new Date();
		note.save();
		findNoteByNotebook(noteBookId);
	}
	/*
	 * 根据id查询对应的笔记
	 * 参数：id
	 * 返回：对应的记录
	 * */
	public static void findNoteById(String id){
		wsOk(Note.findById(id));
	}
	
	public static void findNoteById2(String noteId){
     Note note = Note.findById(noteId);
     render("/NoteMgm/noteInfo.html",note);
	}
	
	/*
	 * 根据id查询对应的笔记本
	 * 参数：id
	 * 返回：对应的记录
	 * */
	public static void findNoteBookById(String id){
		wsOk(NoteBook.findById(id));
	}
	/*
	 * 根据id删除笔记
	 * 参数：ID，笔记本ID
	 * 返回：全部列表
	 * */
	public static void deleteNote(String id,String noteBookId){
		/*NoteBookToNote.delete("noteId", id);*/
		Note.delete("id = ?", id);
		findNoteByNotebook(noteBookId);
	}
	/*
	 * 根据id删除笔记本
	 * 参数：ID
	 * 返回：全部列表
	 * */
	public static void deleteNoteBook(String id ,String userId){
		/*List<NoteBookToNote> list =NoteBookToNote.findByNoteBook(id);
		NoteBookToNote.delete("noteBookId = ?", id);
		//循环删除笔记
		for(NoteBookToNote n : list){
			Note.delete("id = ?", n.noteId);
		}*/
		//循环删除笔记
		List<Note> notes = Note.find("noteBookId = ?", id).fetch();
		for(Note n : notes){
			n.delete();
		}
		NoteBook.delete("id = ?", id);
		findNoteBookByUser1(userId);
	}
	
	/*
	 * 根据名称查询笔记本
	 * 参数：用户ID，笔记本名称
	 * 返回：全部列表
	 * */
	public static void findNoteBookByName(String  userId,String noteBookName){
		/*List<NoteBook> noteBooklist = NoteBook.find("select nb from NoteBook nb,UserToNoteBook unb where nb.id = unb.noteBookId and unb.userId = ? and nb.name = ?",userId,noteBookName).fetch();*/
		
		List<NoteBook> noteBooklist = NoteBook.find("select nb from NoteBook nb where nb.userId = ? and nb.name = ?", userId,noteBookName).fetch();
		wsOk(noteBooklist);
		
	}
	
	/*
	 * 根据标题查询笔记
	 * 参数：用户ID，笔记标题
	 * 返回：全部列表
	 * */
    public static void findNoteByTitle(String userId,String title){
	  
	  /*List<NoteBookToNote> noteBookToNotes = NoteBookToNote.find("select n from NoteBookToNote n,(select nb from NoteBook nb,UserToNoteBook unb where nb.id = unb.noteBookId and unb.userId = ?) temp where temp.noteBookId = n.noteBookId", userId).fetch();
	  List<Note> notes = new ArrayList();
	  for(NoteBookToNote nb :noteBookToNotes){
		  List<Note> notelist = Note.find("select from Note where id = ? and title like ?",nb.noteId,"%"+title+"%").fetch();
		  notes.add(notelist.get(0));  
	  }*/
    	List<Note> notes = Note.find("select n from Note n,NoteBook nb where n.noteBookId = nb.id and nb.userId = ? and n.title like ?", userId,"%"+title+"%").fetch();
	  wsOk(notes);
  }
	
	/*
	 * 根据内容查询笔记
	 * 参数：用户ID，笔记内容
	 * 返回：全部列表
	 * */
    
    public static void findNoteByContent(String userId,String content){
  	  
  	  /*List<NoteBookToNote> noteBookToNotes = NoteBookToNote.find("select n from NoteBookToNote n,(select nb from NoteBook nb,UserToNoteBook unb where nb.id = unb.noteBookId and unb.userId = ?) temp where temp.noteBookId = n.noteBookId", userId).fetch();
  	  List<Note> notes = new ArrayList();
  	  for(NoteBookToNote nb :noteBookToNotes){
  		  List<Note> notelist = Note.find("select from Note where id = ? and content like ?",nb.noteId,"%"+content+"%").fetch();
  		  notes.add(notelist.get(0));  
  	  }*/
    	List<Note> notes = Note.find("select n from Note n,(select * from NoteBook where userId = ?) nb where n.noteBookId = nb.id  and n.content like ?", userId,"%"+content+"%").fetch();
  	  wsOk(notes);
    }
    
}
