package com.qf.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Email;
import com.qf.entity.User;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {
    @Reference
    IUserService iuserService;
    @RequestMapping("")
    public String home(){
        return "home";
    }
    @RequestMapping("/tologin")
    public String tologin(){
        return "userlogin";
    }
    @RequestMapping("toregister")
    public String toregister(){
        return "usertoregister";
    }

    @ResponseBody
    @RequestMapping("/verify")
    public Object verify(String mail, HttpSession httpSession){
        long number= (long) ((Math.random()*(9000))+1000);
        Email email=new Email();
//        email.setRecipients(mail);
        email.setRecipients(mail);
        email.setSendtime(new Date());
        String content=number+"";
        email.setContent(content);
        email.setHeadline("验证码");
        email.setAddresser("18379218654@sina.cn");
        int i = iuserService.sendverify(email);
        httpSession.setAttribute("content",content);
        return i;
    }

    @RequestMapping("/register")
    public String register(User user,HttpSession httpSession){
        String number = (String) httpSession.getAttribute("content");
        String str=user.getVerifycode();
        if (str.equals(number)){
            int insert = iuserService.insert(user);
            return "chenggong";
        }
       return "cuowu";
    }
    @RequestMapping("/userlogin")
    public String userlogin(User user,Model model){
        User userlogin = iuserService.userlogin(user);
        model.addAttribute("userlogin",userlogin);
        return "userlist";
    }

    @RequestMapping("/find")
    public String find(){
        return "find";
    }
    @RequestMapping("/findPassword")
    public int findPassword(String name){
        User user=iuserService.userByName(name);
        System.out.println(user);
        System.out.println(user.getEmail());
        Email email=new Email();
        email.setRecipients(user.getEmail());
        email.setSendtime(new Date());
        String str="请点击<a href="+"'http://localhost:8080/user/resetpasswod?name="+name+"'>重置密码</a>";
        email.setContent(str);
        System.out.println(str);
        email.setAddresser("18379218654@sina.cn");
        email.setHeadline("找回密码!");
        int sendverify = iuserService.sendverify(email);
        return sendverify;
    }
    @RequestMapping("/resetpasswod")
    public  String resetpasswod(String name,Model model){
        model.addAttribute("name",name);
        return "reset";
    }
    @RequestMapping("/userupdate")
    public String userupdate(String name,String password){
        int i=iuserService.userupdate(name,password);
        if (i>0){
            return "home";
        }
        return "cuowu";
    };
}
