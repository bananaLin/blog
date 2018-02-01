package com.example.junior.Controller;

import com.example.junior.Entity.Article;
import com.example.junior.Entity.Reply;
import com.example.junior.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> addArticle(HttpSession session, Article article)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        String name = "";
        if(session.getAttribute("name")!=null){
            name = session.getAttribute("name").toString();
        }else{
            data.put("result", "请先登录");
        }
        article.setAuthor(name);
        boolean result = articleService.addArticle(article);
        data.put("result", result);
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/list/{index}/{rows}", method = RequestMethod.GET)
    public Map<String, Object> listArticles(@PathVariable int index, @PathVariable int rows)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        List<Article> articles = articleService.listArticle(index, rows);
        data.put("list", articles);
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Map<String, Object> updateArticle(Article article)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        boolean result = articleService.updateArticle(article);
        data.put("result", result);
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteArticle(@PathVariable String id)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        boolean result = articleService.deleteArticle(id);
        data.put("result", result);
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public Map<String, Object> reply(Reply reply, HttpSession session)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        if(session.getAttribute("name")==null || session.getAttribute("name")=="")
        {
            data.put("result","评论需要先登陆");
            return data;
        }
        String name = session.getAttribute("name").toString();
        reply.setName(name);
        Boolean result =  articleService.replay(reply);
        if(result)
        {
            data.put("result","评论成功，管理正在审核中...");
        }
        else
        {
            data.put("result","评论失败");
        }
        return data;
    }

}
