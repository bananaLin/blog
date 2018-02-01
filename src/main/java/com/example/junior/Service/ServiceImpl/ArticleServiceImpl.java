package com.example.junior.Service.ServiceImpl;

import com.example.junior.Dao.ArticleDao;
import com.example.junior.Entity.Article;
import com.example.junior.Entity.Reply;
import com.example.junior.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean addArticle(Article article)
    {

        if(article.getTitle() =="" || article.getTitle() == null || article.getContent()=="" ||article.getContent()==null)
            return false;
        article.setTime(new Date());
        return articleDao.insert(article)!=null;
    }

    public List<Article> listArticle(int index, int rows)
    {
        if(index < 1 || rows < 1){
            return new ArrayList<Article>();
        }
        Query query = new Query();
        Sort sort = new Sort(Sort.Direction.DESC, "time");
        query.with(sort).with(new PageRequest(index-1, rows));
        return mongoTemplate.find(query, Article.class);
    }

    public boolean updateArticle(Article article)
    {
        article.setTime(new Date());
        return articleDao.save(article).getId()!=null;
    }

    @Override
    public boolean replay(Reply reply) {
        if(reply==null)
            return false;
        if(reply.getContent()== null|| reply.getName() == null)
            return false;
        reply.setCheck(false);
        reply.setTime(new Date());
        mongoTemplate.save(reply);
        return true;
    }

    public boolean deleteArticle(String id)
    {
        Query query = Query.query(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, Article.class).getN() == 1;
    }
}
