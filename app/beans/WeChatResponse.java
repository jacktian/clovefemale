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
	 * 位0x0001被标志时，星标刚收到的消息。
	 * PS：其实，这个我不知道是什么来的
	 */
	public int funcFlag;
}
