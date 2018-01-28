package com.example.junior.Service;

import com.example.junior.Entity.Article;
import com.example.junior.Entity.Reply;
import com.example.junior.Entity.UserEntity;

import java.util.List;

public interface UserService {

    public boolean register(UserEntity userEntity);

    public boolean login(String name, String password);

    public boolean addArticle(Article article);

    public List<Article> listArticle(int index, int rows);

    public boolean updateArticle(Article article);

    public boolean replay(Reply reply);

    public boolean deleteArticle(String id);

    public boolean active(int id);

    public String upload(byte[] file);
}
