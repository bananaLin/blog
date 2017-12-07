package com.example.junior.Dao;

import com.example.junior.Entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleDao extends MongoRepository<Article, String> {

}
