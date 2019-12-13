package com.zhj.demo1.service;

import com.zhj.demo1.model.User;

import java.util.HashMap;

public interface LoginService {
    public HashMap insertSelective(User userVo);
    public HashMap selectUser(String name);
}
