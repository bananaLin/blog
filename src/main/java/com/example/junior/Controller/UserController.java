package com.example.junior.Controller;

import com.example.junior.Entity.Article;
import com.example.junior.Entity.Reply;
import com.example.junior.Entity.UserEntity;
import com.example.junior.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
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




}
