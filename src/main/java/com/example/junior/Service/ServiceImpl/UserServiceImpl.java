package com.example.junior.Service.ServiceImpl;

import com.example.junior.Dao.ArticleDao;
import com.example.junior.Dao.UserDao;
import com.example.junior.Entity.Article;
import com.example.junior.Entity.UserEntity;
import com.example.junior.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean register(UserEntity userEntity)
    {
        Date now = new Date();
        userEntity.setCreateTime(now);
        userEntity.setModifyTime(now);
        userEntity.setPassword(toMD5(userEntity.getPassword()));
        userEntity.setIsCheck(false);//默认未激活
        userEntity.setIsSend(false);//默认未发送过邮件
        return userDao.register(userEntity) > 0;
    }

    public boolean login(String name, String password)
    {
        password = toMD5(password);
        return userDao.login(name, password) > 0;
    }

    public boolean addArticle(Article article)
    {

        if(article.getTitle().trim().isEmpty() || article.getContent().trim().isEmpty())
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

    public boolean deleteArticle(String id)
    {
        Query query = Query.query(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, Article.class).getN() == 1;
    }

    public boolean active(int id)
    {
        UserEntity userEntity = userDao.getUserById(id);
        if(userEntity!=null)
        {
            userEntity.setModifyTime(new Date());
            return userDao.activateUser(userEntity) > 0;
        }
        return false;
    }

    //用户密码用MD5加密
    public String toMD5(String password)
    {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");//生成一个MD5加密计算摘要
            md.update(password.getBytes());// 调用update方法计算MD5函数(参数：将密码串转换为操作系统的字节编码)
            //digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            //BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        }catch (Exception e) {
            System.out.println("发生"+e.toString()+"的错误");
            return "password";
        }
    }

}
