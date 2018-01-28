package com.example.junior.Service.ServiceImpl;

import com.example.junior.Dao.UserDao;
import com.example.junior.Entity.UserEntity;
import com.example.junior.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.List;

@Component
public class TaskServiceImpl implements TaskService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(fixedRate = 5000)
    public void timer()
    {
        //System.out.println("例行检查未激活用户");
        List<UserEntity> users = userDao.checkUser();
        for(UserEntity user : users)
        {
            if(!user.getIsSend()){
                sendMail(user);
            }
            userDao.sendedMail(user.getId());
        }

    }

    public Boolean sendMail(UserEntity userEntity)
    {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper  = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("2973925586@qq.com");
            helper.setTo(userEntity.getMail());
            helper.setSubject("请激活你的账号");
            String html = "<div size='2'><p>"+ userEntity.getName() +",你好</p>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;感谢你注册本论坛<br/>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;请点击该链接激活账号,<a href='http://127.0.0.1:8080/user/active/"+userEntity.getId()+"'>请点击这里激活</a><br/>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;该邮件由系统自动发送，请勿回复。<br/>";
            html += "<span>============================================================</span>";
            html += "<p size='2'>林氏网络科技有限公司</p>";
            helper.setText(html, true);
            mailSender.send(mimeMessage);
            System.out.println("Successfully");
            return true;
        } catch (Exception e) {
            System.out.println("fail,because:"+e.toString());
            return false;
        }
    }
}
