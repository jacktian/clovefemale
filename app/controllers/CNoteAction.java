package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import play.db.jpa.JPA;
import play.mvc.Http.Request;

import beans.NoteBean;
import beans.SimpleNoteBookBean;

import com.google.gson.Gson;

import models.Note;
import models.NoteBook;

/**
 * 客户端笔记控制器
 * 
 * @author Cater
 * @since 2015/3/10
 */
public class CNoteAction extends WebService{

	public static void  findAllNoteBook(){
		List<NoteBook> list = NoteBook.findAll();
//		wsOk(list);
		wsOkAsJsonP(list);
	}
	
	//只获取每个笔记本的名称与id
	public static void findNoteBookByUserId(String userId){		
		String queryString = "select new beans.SimpleNoteBookBean " +
						" (nb.id,nb.name) " +
						" from NoteBook as nb where nb.userId=?1 order by nb.createDate";//
		Query query = JPA.em().createQuery(queryString);
		query.setParameter(1, userId);//给编号为1的参数设值 
		List<SimpleNoteBookBean> nblist  = query.getResultList();
		wsOkAsJsonP(nblist);
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
		/*List<NoteBookToNote> list = NoteBookToNote.findByNoteBook(noteBookId);
		List<Note> notes = new ArrayList<>();
		for(NoteBookToNote n : list){
			Note note  = Note.findById(n.noteId);
			notes.add(note);
		}*/
		List<Note> notes = Note.find("noteBookId = ?", noteBookId).fetch();
		wsOk(notes);
	}
	/*
	 * 根据userId查询所有的笔记本
	 * 参数：userId
	 * 返回：满足条件的列表
	 * */
	public static void findNoteBookByUser(String userId){
		/*List<UserToNoteBook> list =UserToNoteBook.findByUser(userId);
		List<NoteBook> noteBooks = new ArrayList();
		for(UserToNoteBook userToNoteBook : list){
			NoteBook n= NoteBook.findById(userToNoteBook.noteBookId);
			noteBooks.add(n);
		}*/
		List<NoteBook> noteBooks = NoteBook.find("userId = ?", userId).fetch();
		wsOk(noteBooks);
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
	 * 增加笔记本
	 * 参数：用户Id，笔记本名称
	 * 
	 */
	public static void addNoteBook2(String userId,String noteBookName){
		try{
			NoteBook notebook = new NoteBook();
			notebook.createDate = new Date();
			notebook.name = noteBookName;
			notebook.userId = userId;
			notebook.recentMFDate = new Date();
			notebook.save();
			SimpleNoteBookBean simpleNoteBook = new SimpleNoteBookBean();
			simpleNoteBook.id = notebook.id;
			simpleNoteBook.name = notebook.name;
			wsOkAsJsonP(simpleNoteBook);
		}catch(Exception e){
			wsErrorAsJsonP(null);
		}
	}
	
	/*
	 * 新增笔记本
	 * 参数：前台传回的实体类，用户id
	 * 返回：全部列表
	 * */
	public static void addNoteBook(NoteBook noteBook,String userId){
	  NoteBook n = noteBook.save();
	  /*NoteBook.createUtoN(userId, n.id);*/
	  findNoteBookByUser(userId);
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
	 * 新增笔记
	 * 参数：笔记标题、笔记内容、所属笔记本Id
	 * 返回操作成功(id)/失败
	 * 
	 */
	public static void addNote2(String noteTitle,String noteContent,String noteBookId){ 
		Note note = new Note();
		note.content = noteContent;
		note.createDate = new Date();
		note.recentMFDate = new Date();
		note.title = noteTitle;
		note.noteBookId = noteBookId;
		try{
			note.save();
			wsOkAsJsonP(note.id);
		}catch(Exception e){
			wsErrorAsJsonP(null);
		}
		
	}
	/*
	 * 更新笔记本信息
	 * 参数：noteBook类，用户id
	 * 返回：全部列表
	 * */
	public static void updateNoteBook(NoteBook noteBook,String userId){
		noteBook.recentMFDate=new Date();
		noteBook.save();
		findNoteBookByUser(userId);
	}
	
	/*
	 *笔记本重命名 
	 * 参数：noteBookId,name
	 * 返回:操作成功/失败
	 */
	public static void renameNb(String noteBookId,String name){
		try{
			NoteBook nb= NoteBook.findById(noteBookId);
			if(nb!=null){
				nb.name =  name;
				nb.recentMFDate = new Date();
				nb.save();
				wsOkAsJsonP("修改成功");
			}else{
				wsOkAsJsonP("修改失败，不存在该笔记");
			}
		}catch(Exception e){
			wsErrorAsJsonP("操作异常");
		}
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
	
	public static void updateNote2(String noteId,String noteTitle,String noteContent,Date createDate,String noteBookId){
		Note note = findNoteByNotedId(noteId);
		if(note!=null){
			note.title =  noteTitle;
			note.content = noteContent;
			note.createDate = createDate;
			note.recentMFDate=new Date();
			note.noteBookId = noteBookId;
			note.save();
			wsOkAsJsonP("修改成功");
		}else{
			wsOkAsJsonP("修改失败，不存在该笔记");
		}
		
	}
	
	/*
	 * 根据id查询对应的笔记
	 * 参数：id
	 * 返回：对应的记录
	 * */
	public static Note findNoteByNotedId(String noteId){
		return Note.findById(noteId);
//		wsOkAsJsonP(Note.findById(noteId));
	}
	
	/*
	 * 根据id查询对应的笔记
	 * 参数：id
	 * 返回：对应的记录Json格式
	 * */
	public static void findNoteById(String noteId){
		wsOkAsJsonP(Note.findById(noteId));
	}
	
	/*
	 *根据noteId查询对应笔记详情，封装成NoteDetailBean对象格式
	 *参数：noteId
	 *返回相应记录
	 * 
	 */
	public static void findNoteDetailById(String noteId){
		wsOk(Note.findById(noteId));
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
	 * 删除笔记
	 * 参数：noteIdList
	 * 返回操作结果（成功/失败）
	 */
	public static void delNote(String[] noteIdList){
		try{
			if(noteIdList!=null){
				for(int i = 0; i < noteIdList.length; i++){
					Note.delete("id = ?", noteIdList[i]);
				}
			}
			wsOkAsJsonP(null);
		}catch(Exception e){
			wsErrorAsJsonP(null);
		}
		
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
		findNoteBookByUser(userId);
		
	}
	
	/*
	 * 删除笔记本
	 * 参数noteBookId
	 * 返回：操作成功或失败
	 */
	public static void delNoteBook(String noteBookId){
		try{
			List<Note> notes = Note.find("noteBookId = ?", noteBookId).fetch();
			for(Note n : notes){
				n.delete();
			}
			NoteBook.delete("id = ?", noteBookId);
			wsOkAsJsonP(null);
		}catch(Exception e){
			wsErrorAsJsonP(null);
		}
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
    
    /*
     * 根据keyword在笔记title和content中搜索相关内容
     * 参数userId、keyWord
     * 返回搜索结果
     */
     public static void findNoteByKeyWord(String userId, String keyword){
    	 try{
	    	 String queryString = "select new beans.NoteBean " +
	 				" (n.id,n.title,substring(n.content,0,49),year(n.createDate),month(n.createDate),day(n.createDate) ,n.recentMFDate,n.noteBookId,nb.name) " +
	 				"from Note as n,NoteBook as nb where (n.noteBookId = nb.id) and (n.title like ?1 or n.content like ?2) and nb.userId=?3 order by n.createDate desc ";//";
	 		Query query = JPA.em().createQuery(queryString);
	 		query.setParameter(1, "%"+keyword+"%");//给编号为1的参数设值 
	 		query.setParameter(2, "%"+keyword+"%");//给编号为2的参数设值 
	 		query.setParameter(3, userId);//给编号为2的参数设值
	 		List<NoteBean> noteBeanList = query.getResultList();
	 		
	 		int lenght = noteBeanList.size();
	 		int keywordLenght = keyword.length();
	 		int index = -2;
	 		String highLightString = "<span class='highLight'>" + keyword + "</span>";
	 		String title="";
	 		String content = "";
	 		NoteBean note;
	 		for(int i = 0; i < lenght; i++){
	 			
	 			note = noteBeanList.get(i);
	 			title = note.title;
	 			content = note.content;
	// 			index = note.title.indexOf(keyword);
	 			title = title.replaceAll(keyword, highLightString);
	 		    note.title = title;
	 		    content = content.replaceAll(keyword,highLightString);
	 		    note.content = content;
	 		}
	 		
	 		wsOkAsJsonP(noteBeanList);
    	 }catch(Exception e){
    		 wsErrorAsJsonP(null);
    	 }
     }
    
    /*
     * 根据用户id查询所有笔记
     * 参数：userId：用户Id
     * 返回：笔记列表,存储对象为NoteBean
     */
    public static void findNoteByUserId(String userId){
    	String queryString = "select new beans.NoteBean " +
				" (n.id,n.title,substring(n.content,0,49),year(n.createDate),month(n.createDate),day(n.createDate) ,n.recentMFDate,n.noteBookId,nb.name) " +
				"from Note as n,NoteBook as nb where n.noteBookId = nb.id and nb.userId=?1 order by n.createDate desc ";
		Query query = JPA.em().createQuery(queryString);
		query.setParameter(1, userId);//给编号为1的参数设值 
		List<NoteBean> noteBeanList = query.getResultList();
		wsOkAsJsonP(noteBeanList);
    }
    
    /*
     * 查询笔记本里的笔记
     * notebookId
     * 返回操作成功/失败
     */
    public static void findNoteByUserIdAndBookId(String notebookId){
    	String queryString = "select new beans.NoteBean " +
				" (n.id,n.title,substring(n.content,0,49),year(n.createDate),month(n.createDate),day(n.createDate) ,n.recentMFDate,n.noteBookId,nb.name) " +
				"from Note as n,NoteBook as nb where n.noteBookId = nb.id and n.noteBookId =?1 order by n.createDate desc ";//and nb.userId=?1";
		Query query = JPA.em().createQuery(queryString);
		query.setParameter(1, notebookId);//给编号为1的参数设值 
		List<NoteBean> noteBeanList = query.getResultList();
		wsOkAsJsonP(noteBeanList);
    }
    
    /*
     * 根据noteId和keyword查找笔记，比高亮其中的keyword
     * 返回相应的笔记记录 
     */
    public static void findSearchNoteDetailByNoteIdAndKeyword(String userId,String noteId,String keyword){
    	String queryString = "select new beans.NoteBean " +
 				" (n.id,n.title,n.content,year(n.createDate),month(n.createDate),day(n.createDate) ,n.recentMFDate,n.noteBookId,nb.name) " +
 				"from Note as n,NoteBook as nb where n.noteBookId = nb.id and n.id = ?1 order by n.createDate desc ";//and nb.userId=?1";
 		Query query = JPA.em().createQuery(queryString);
 		query.setParameter(1, noteId);//给编号为1的参数设值 
 		List<NoteBean> noteBeanList = query.getResultList();
 		
 		int lenght = noteBeanList.size();
 		String highLightString = "<span class='highLight'>" + keyword + "</span>";
 		String title="";
 		String content = "";
 		NoteBean note;
 		for(int i = 0; i < lenght; i++){
 			note = noteBeanList.get(i);
 			title = note.title;
 			content = note.content;
 			title = title.replaceAll(keyword, highLightString);
 		    note.title = title;
 		    content = content.replaceAll(keyword,highLightString);
 		    note.content = content;
 		}
 		wsOkAsJsonP(noteBeanList);
    }
}
