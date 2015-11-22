package controllers;

import models.Article;

import java.util.List;

/**
 * Created by boxizen on 15/11/22.
 */
public class ArticleAction extends WebService  {

    public static void getArticle() {
        List<Article> articleList = Article.findAll();
        wsOkAsJsonP(articleList);
    }

    public static void addArticle(String title, String picurl, String url, String description, String prioty, String type) {

        List<Article> articleList = Article.find("byTypeAndPrioty",type,prioty).fetch();

        Article article = null;

        if(articleList.size() > 0) {
            article = articleList.get(0);

        }

        if(article == null) {
            article = new Article();
        }

        article.title = title;
        article.picUrl = picurl;
        article.url = url;
        article.description = description;
        article.prioty = prioty;
        article.type = type;
        article.save();

        wsOkAsJsonP(Article.findAll());
    }
}
