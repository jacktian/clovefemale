package beans;

import java.util.List;

public class WeChatPicMsgResponse {
	/**
	 * 接收方帐号（收到的OpenID）
	 */
	public String toUserName;
	
	/**
	 * 开发者微信号
	 */
	public String fromUserName;
	
	/**
	 * 消息创建时间
	 */
	public Long createTime;
	
	/**
	 * 消息创建类型
	 */
	public String msgType;
	
	/**
	 * 文章数目
	 */
	public int articleCount;
	
	/**
	 * 文章内容
	 */
	public List<PicArticle> articleList;
	
	/**
	 * 重写toString方法，转换成微信服务器能接收的XML格式
	 */
	
	@Override
	public String toString(){
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<xml>");
		strBuilder.append("<ToUserName><![CDATA["+toUserName+"]]></ToUserName>");
		strBuilder.append("<FromUserName><![CDATA["+fromUserName+"]]></FromUserName>");
		strBuilder.append("<CreateTime>"+createTime+"</CreateTime>");
		strBuilder.append("<MsgType><![CDATA["+msgType+"]]></MsgType>");
		strBuilder.append("<ArticleCount>"+articleCount+"</ArticleCount>");
		strBuilder.append("<Articles>");
		for(int i=0;i<articleCount;i++){
			strBuilder.append("<item>");
			strBuilder.append("<Title><![CDATA["+articleList.get(i).title+"]]</Title>");
			strBuilder.append("<Description><![CDATA["+articleList.get(i).desciption+"]]</Description>");
			strBuilder.append("<PicUrl><![CDATA["+articleList.get(i).picUrl+"]]</PicUrl>");
			strBuilder.append("<Url><![CDATA["+articleList.get(i).url+"]]</Url>");
			strBuilder.append("</item>");
		}
		strBuilder.append("</Articles>");
		strBuilder.append("</xml>");
		return strBuilder.toString();
	}
}
