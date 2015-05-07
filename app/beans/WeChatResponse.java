package beans;

public class WeChatResponse {
	
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
	 * 消息内容
	 */
	public String content;
	
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
		strBuilder.append("<Content><![CDATA["+content+"]]></Content>");
		strBuilder.append("</xml>");
		return strBuilder.toString();
	}
}
