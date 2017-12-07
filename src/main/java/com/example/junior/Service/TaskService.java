package com.example.junior.Service;

import com.example.junior.Entity.UserEntity;

public interface TaskService {
    public void timer();

    public Boolean sendMail(UserEntity userEntity);
}
