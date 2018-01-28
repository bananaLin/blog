package com.example.junior.Controller;

import com.example.junior.Entity.Article;
import com.example.junior.Entity.Reply;
import com.example.junior.Entity.UserEntity;
import com.example.junior.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public Map<String, Object> register(UserEntity userEntity)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        Boolean result = userService.register(userEntity);
        data.put("result",result);
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(HttpSession httpSession, String name, String password)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        if(name.trim().isEmpty())
        {
            data.put("result","请输入用户名！");
            return data;
        }else if(password.trim().isEmpty())
        {
            data.put("result","请输入密码！");
            return data;
        }
        Boolean result = userService.login(name, password);
        if(result)
        {
            data.put("result", "登录成功");
            httpSession.setAttribute("name", name);
        }
        else
        {
            data.put("result", "用户名或密码错误");
        }
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    public Map<String, Object> addArticle(HttpSession session, Article article)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        String name = session.getAttribute("name").toString();
        article.setAuthor(name);
        boolean result = userService.addArticle(article);
        data.put("result", result);
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/list/{index}/{rows}", method = RequestMethod.GET)
    public Map<String, Object> listArticles(@PathVariable int index, @PathVariable int rows)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        List<Article> articles = userService.listArticle(index, rows);
        data.put("list", articles);
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/update/article", method = RequestMethod.POST)
    public Map<String, Object> updateArticle(Article article)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        boolean result = userService.updateArticle(article);
        data.put("result", result);
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteArticle(@PathVariable String id)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        boolean result = userService.deleteArticle(id);
        data.put("result", result);
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/active/{id}", method = RequestMethod.GET)
    public Map<String, Object> sendMail(@PathVariable int id)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        boolean result = userService.active(id);
        if(result){
            System.out.println("用户已激活");
            data.put("result", "用户已激活");
        }else{
            System.out.println("用户激活失败");
            data.put("result", "用户激活失败");
        }
        return data;
    }

    @RequestMapping(value="/upload", method = RequestMethod.GET)
    public String upload() {
        return "upload";
    }

    @RequestMapping(value="/uploadImage", method = RequestMethod.POST)
    public @ResponseBody String uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        try {
            userService.upload(file.getBytes());
        }catch (Exception e){

        }
        return "uploadimg success";
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
        Boolean result =  userService.replay(reply);
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
