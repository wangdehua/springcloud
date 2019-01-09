package com.xiaoxiao.client.service;


import com.xiaoxiao.client.pojo.User;
import org.springframework.stereotype.Component;

@Component
public class UserClientServiceCallBack implements UserClientService {

    /**
     *  如果跑出异常 , 可以在这里添加异常信息
     * @param userId
     * @return
     */
    @Override
    public User getUserInfoByUserId(Integer userId) {
        return null;
    }

    @Override
    public User getUserInfoByPhone(String phone) {
        return null ;
    }

    @Override
    public boolean regUser(User user) {
        return false;
    }

    @Override
    public boolean updateUserInfo(User user) {
        return false;
    }
}
