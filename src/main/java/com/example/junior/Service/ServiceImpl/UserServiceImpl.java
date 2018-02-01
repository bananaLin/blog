package com.example.junior.Service.ServiceImpl;

import com.example.junior.Dao.UserDao;
import com.example.junior.Entity.UserEntity;
import com.example.junior.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;



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
