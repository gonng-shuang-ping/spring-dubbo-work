package com.qf.Service;

import com.qf.entity.Email;
import com.qf.entity.User;

public interface IUserService {
    public int insert(User user);
    public User userlogin(User user);
//   public void sendverify(String mail,long number);
    public int sendverify(Email email);
    public User userByName(String name);

    public  int userupdate(String name, String password);
}
