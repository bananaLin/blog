package com.example.junior.Dao;

import com.example.junior.Entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    public int register(UserEntity userEntity);

    public int login(@Param("name") String name, @Param("password") String password);

    public List<UserEntity> checkUser();

    public int activateUser(UserEntity userEntity);

    public UserEntity getUserById(@Param("id") int id);

    public void sendedMail(@Param("id") int id);
}
