package com.qf.service.Impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.Service.IEmailService;
import com.qf.Service.IUserService;
import com.qf.dao.UserDao;
import com.qf.dao.UserMapper;
import com.qf.entity.Email;
import com.qf.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserDao userDao;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private JavaMailSender javaMailSender;
//    @Reference
//    IEmailService emailService;
    @Override
    public int insert(User user) {
        int insert = userDao.insert(user);
        return insert;
    }

    @Override
    public User userlogin(User user) {
        User userlogin = userMapper.userlogin(user);
        return userlogin;
    }

    @Override
    public int sendverify(Email email){
        try {
            //创建一封邮件
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //创建一个Spring提供的邮件帮助对象
            MimeMessageHelper messageHelper = null;
            messageHelper = new MimeMessageHelper(mimeMessage, true);
            //设置发送方
            messageHelper.setFrom(email.getAddresser());
            //设置接收方
            //to - 普通接收方
            //cc - 抄送方
            //bcc - 密送方
            messageHelper.addTo(email.getRecipients());
            //设置标题
            messageHelper.setSubject(email.getHeadline());
            //设置内容
            messageHelper.setText(email.getContent(),true);
            //设置时间
            messageHelper.setSentDate(email.getSendtime());
            //发送邮件
            javaMailSender.send(mimeMessage);
            return 1;
        }catch (MessagingException e) {
            return 0;
        }

    }

    @Override
    public User userByName(String name) {
        User user = userMapper.userByName(name);
        return user;
    }

    @Override
    public int userupdate(String name, String password) {
        Map<String,String> map=new HashMap<>();
        map.put("name",name);
        map.put("password",password);
       int i= userMapper.userupdate(map);
        return i;
    }
//    @Override
//    public void sendverify(String mail,long number) {
//        Email email=new Email();
//        email.setRecipients(mail);
//        email.setSendtime(new Date());
//        String content=number+"";
//        email.setContent(content);
//        email.setAddresser("18379218654@sina.cn");
//        emailService.send(email);
//    }
}
