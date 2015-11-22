package models;

import com.sudocn.play.BasicModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by boxizen on 15/11/22.
 */

@Entity
@Table(name = "article")
public class Article extends BasicModel{

    /**
     * 文章标题
     */
    @Column(name = "title")
    public String title;

    /**
     * 图片链接
     */
    @Column(name = "picurl")
    public String picUrl;

    /**
     * 文章链接
     */
    @Column(name = "url")
    public String url;

    /**
     * 文章描述
     */
    @Column(name = "description")
    public String description;

    /**
     * 优先级
     */
    @Column(name = "prioty")
    public String prioty;

    /**
     * 文章类型
     */
    @Column(name = "type")
    public String type;

}
