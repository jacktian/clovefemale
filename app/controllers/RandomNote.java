package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Note;
import models.NoteBook;
import models.NoteBookToNote;
import models.UserToNoteBook;

public class RandomNote extends WebService{

	public static void  findAllNoteBook(){
		List<NoteBook> list = NoteBook.findAll();
		wsOk(list);
	}
	/*
	 * 根据notebookid查询所有的身体指标
	 * 参数：noteBookId
	 * 返回：满足条件的列表
	 * */
	public static void findNoteByNotebook(String noteBookId){
		List<NoteBookToNote> list = NoteBookToNote.findByNoteBook(noteBookId);
		List<Note> notes = new ArrayList<>();
		for(NoteBookToNote n : list){
			Note note  = Note.findById(n.noteId);
			notes.add(note);
		}
		wsOk(notes);
	}
	/*
	 * 根据userId查询所有的笔记本
	 * 参数：userId
	 * 返回：满足条件的列表
	 * */
	public static void findNoteBookByUser(String userId){
		List<UserToNoteBook> list =UserToNoteBook.findByUser(userId);
		List<NoteBook> noteBooks = new ArrayList();
		for(UserToNoteBook userToNoteBook : list){
			NoteBook n= NoteBook.findById(userToNoteBook.noteBookId);
			noteBooks.add(n);
		}
		wsOk(noteBooks);
	}
	
	public static List<NoteBook> findNoteBookByUser2(String userId){
		List<UserToNoteBook> list =UserToNoteBook.findByUser(userId);
		List<NoteBook> noteBooks = new ArrayList();
		for(UserToNoteBook userToNoteBook : list){
			NoteBook n= NoteBook.findById(userToNoteBook.noteBookId);
			noteBooks.add(n);
		}
		return noteBooks;
	}
	/*
	 * 新增笔记本
	 * 参数：前台传回的实体类，用户id
	 * 返回：全部列表
	 * */
	public static void addNoteBook(NoteBook noteBook,String userId){
	  NoteBook n = noteBook.save();
	  NoteBook.createUtoN(userId, n.id);
	  findNoteBookByUser(userId);
	}
	/*
	 * 新增笔记
	 * 参数：前台传回的实体类，笔记本id
	 * 返回：全部列表
	 * */
	public static void addNote(Note note,String noteBookId){
		Note n = note.save();
		Note.createNtoN(noteBookId, n.id);
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
		findNoteBookByUser(userId);
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
		NoteBookToNote.delete("noteId", id);
		Note.delete("id = ?", id);
		findNoteByNotebook(noteBookId);
	}
	/*
	 * 根据id删除笔记本
	 * 参数：ID
	 * 返回：全部列表
	 * */
	public static void deleteNoteBook(String id ,String userId){
		List<NoteBookToNote> list =NoteBookToNote.findByNoteBook(id);
		NoteBookToNote.delete("noteBookId = ?", id);
		//循环删除笔记
		for(NoteBookToNote n : list){
			Note.delete("id = ?", n.noteId);
		}
		NoteBook.delete("id = ?", id);
		findNoteBookByUser(userId);
	}
	
	/*
	 * 根据名称查询笔记本
	 * 参数：用户ID，笔记本名称
	 * 返回：全部列表
	 * */
	public static void findNoteBookByName(String  userId,String noteBookName){
		List<NoteBook> noteBooklist = NoteBook.find("select nb from NoteBook nb,UserToNoteBook unb where nb.id = unb.noteBookId and unb.userId = ? and nb.name = ?",userId,noteBookName).fetch();
		wsOk(noteBooklist);
		
	}
	
	/*
	 * 根据标题查询笔记
	 * 参数：用户ID，笔记标题
	 * 返回：全部列表
	 * */
    public static void findNoteByTitle(String userId,String title){
	  
	  List<NoteBookToNote> noteBookToNotes = NoteBookToNote.find("select n from NoteBookToNote n,(select nb from NoteBook nb,UserToNoteBook unb where nb.id = unb.noteBookId and unb.userId = ?) temp where temp.noteBookId = n.noteBookId", userId).fetch();
	  List<Note> notes = new ArrayList();
	  for(NoteBookToNote nb :noteBookToNotes){
		  List<Note> notelist = Note.find("select from Note where id = ? and title like ?",nb.noteId,"%"+title+"%").fetch();
		  notes.add(notelist.get(0));  
	  }
	  wsOk(notes);
  }
	
	/*
	 * 根据内容查询笔记
	 * 参数：用户ID，笔记内容
	 * 返回：全部列表
	 * */
    
    public static void findNoteByContent(String userId,String content){
  	  
  	  List<NoteBookToNote> noteBookToNotes = NoteBookToNote.find("select n from NoteBookToNote n,(select nb from NoteBook nb,UserToNoteBook unb where nb.id = unb.noteBookId and unb.userId = ?) temp where temp.noteBookId = n.noteBookId", userId).fetch();
  	  List<Note> notes = new ArrayList();
  	  for(NoteBookToNote nb :noteBookToNotes){
  		  List<Note> notelist = Note.find("select from Note where id = ? and content like ?",nb.noteId,"%"+content+"%").fetch();
  		  notes.add(notelist.get(0));  
  	  }
  	  wsOk(notes);
    }
    
}
