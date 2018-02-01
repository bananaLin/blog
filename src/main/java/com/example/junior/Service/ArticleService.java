package com.example.junior.Service;

import com.example.junior.Entity.Article;
import com.example.junior.Entity.Reply;
import java.util.List;

public interface ArticleService {

    public boolean addArticle(Article article);

    public List<Article> listArticle(int index, int rows);

    public boolean updateArticle(Article article);

    public boolean replay(Reply reply);

    public boolean deleteArticle(String id);
}
