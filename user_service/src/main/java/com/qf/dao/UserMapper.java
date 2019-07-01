package com.qf.dao;

import com.qf.entity.User;

import java.util.Map;

public interface UserMapper {
    public User userlogin(User user);
    public User userByName(String name);

    public int userupdate(Map<String, String> map);
}
