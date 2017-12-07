package com.example.junior.Service.ServiceImpl;

import com.example.junior.Dao.LogDao;
import com.example.junior.Entity.LogEntity;
import com.example.junior.Service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogDao logDao;

    @Override
    public void logUserBehaviour(LogEntity logEntity) {
        logDao.logUserBehaviour(logEntity);
    }
}
