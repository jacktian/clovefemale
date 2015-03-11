package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Note;
import models.NoteBook;

public class NBbean {
	/**
	 * 笔记本id
	 * */
	public String id;
	/**
     * 父母ID
     */
	public String userId;
	
	/**
     * 用户姓名
     */
	public String userName;
	
	/**
     * 笔记本名称
     */
	public String name;
	/**
	 * 创建日期
	 * */
	public Date createDate;
	/**
	 * 最新修改日期
	 * */
	public Date recentMFDate;
	/**
	 * 简介
	 * */
	public String introduceContent;
	/**
	 * 笔记数量
	 * */
	public int count;
	/*构建babyBeanList*/
	public static List<NBbean> builList(List<NoteBook> nList){
		List<NBbean> beanList = new ArrayList();
		for(int i=0;i<nList.size();i++){
			NBbean nBean = new NBbean();
			nBean.userId = nList.get(i).userId;
			nBean.name = nList.get(i).name;
			nBean.createDate = nList.get(i).createDate;
			nBean.recentMFDate = nList.get(i).recentMFDate;
			nBean.id =nList.get(i).id;
			nBean.introduceContent=nList.get(i).introduceContent;
			List<Note> counts =  Note.find("select n from Note n where noteBookId=?", nList.get(i).id).fetch();
			nBean.count = counts.size();
			beanList.add(nBean);
		}
		return beanList;
	}
}
