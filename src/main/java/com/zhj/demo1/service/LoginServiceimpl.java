package com.zhj.demo1.service;

import com.zhj.demo1.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginServiceimpl implements LoginService {

    @Override
    public HashMap insertSelective(User userVo) {
        return null;
    }

    @Override
    public HashMap selectUser(String name) {
        HashMap messageMap = new HashMap();
        HashMap bodyMap = new HashMap();
        messageMap.put("status", "成功");
        bodyMap.put("result", "用户名不存在");
        messageMap.put("body", bodyMap);
        return messageMap;
    }
}
