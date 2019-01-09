package com.xiaoxiao.server.controller;

import com.xiaoxiao.server.pojo.User;
import com.xiaoxiao.server.service.UserServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserServerController {

    @Autowired
    private UserServerService userServerService ;

    /**
     * g根据用户id获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserInfoById/{userId}")
    public User getUserInfoById(@PathVariable(value = "userId") Integer userId){
        return this.userServerService.getUserInfoById(userId);
    }

    /**
     * 登录
     * @param phone
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public User login(@RequestParam(value = "phone",required = true)String phone,
                      @RequestParam(value = "password",required = true)String password){
        return this.userServerService.login(phone,password) ;
    }

    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    @RequestMapping(value = "/getUserInfoByPhone/{phone}")
    public User getUserInfoByPhone(@PathVariable(value = "phone")String phone){
        return this.userServerService.getUserInfoByPhone(phone) ;
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/reg",method = RequestMethod.POST)
    public boolean registerUser(@RequestBody User user){
        return this.userServerService.registerUser(user) ;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public boolean updateUserInfo(@RequestBody User user){
        return this.userServerService.updateUserInfo(user) ;
    }
}
