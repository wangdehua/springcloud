package com.xiaoxiao.server.service.impl;

import com.xiaoxiao.server.dao.UserMapper;
import com.xiaoxiao.server.pojo.User;
import com.xiaoxiao.server.service.UserServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServerServiceImpl implements UserServerService {

    @Autowired
    private UserMapper userMapper ;

    @Override
    public User getUserInfoById(Integer userId) {
        return this.userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public User getUserInfoByPhone(String phone) {
        return this.userMapper.getUserInfoByPhone(phone);
    }

    @Override
    public User login(String phone, String password) {
        Map<String,String> param = new HashMap<>(2);
        param.put("phone",phone);
        param.put("password",password);
        return this.userMapper.login(param);
    }

    @Override
    public boolean registerUser(User user) {
        return this.userMapper.insert(user) == 1 ;
    }

    @Override
    public boolean updateUserInfo(User user) {
        return this.userMapper.updateByPrimaryKey(user) == 1 ;
    }
}
