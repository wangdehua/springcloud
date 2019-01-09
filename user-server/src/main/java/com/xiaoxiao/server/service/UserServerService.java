package com.xiaoxiao.server.service;

import com.xiaoxiao.server.pojo.User;

public interface UserServerService {

    User getUserInfoById(Integer userId);

    User getUserInfoByPhone(String phone);

    User login(String phone,String password);

    boolean registerUser(User user) ;

    boolean updateUserInfo(User user);
}
