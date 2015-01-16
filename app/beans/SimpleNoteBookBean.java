package beans;

/*
 * 简单的笔记本类
 * 
 * 用与客户端笔记本列表
 * 
 *	@author cater
 * @since 1/13/15
 */
public class SimpleNoteBookBean {
	/*
	 * 笔记本id
	 */
	public String id;
	
	/*
	 * 笔记本名称
	 */
	public String name;
	
	public SimpleNoteBookBean(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public SimpleNoteBookBean(){
		
	}
}
