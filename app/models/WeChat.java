package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;

/**
 * 微信
 *
 * @author boxiZen
 * @since 2015/3/24
 */

@Entity
@Table(name = "wechat")
public class WeChat extends BasicModel{
	
	/**
     * access_token
     */
	@Column(name = "access_token")
	public String access_token;
	
	/**
     * access_token
     */
	@Column(name = "js_ticket")
	public String js_ticket;
}
