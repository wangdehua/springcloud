package com.xiaoxiao.client.controller;

import com.xiaoxiao.client.pojo.User;
import com.xiaoxiao.client.service.UserClientService;
import com.xiaoxiao.client.utils.ExceptionEnum;
import com.xiaoxiao.client.utils.Ret;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserClientController {

    @Autowired
    private UserClientService userClientService ;

    /**
     *  根据用户编号获取用户信息
     * @param userId
     * @return
     */
    @GetMapping(value = "/getUserInfoById/{userId}")
    public Ret getUserInfoById(@PathVariable("userId")Integer userId){
        if(userId == null){
            return Ret.err(1,0,ExceptionEnum.PARAM_EXCEPTION.getName());
        }
        return Ret.success(this.userClientService.getUserInfoByUserId(userId));
    }

    /**
     *  根据用户手机号获取用户信息
     * @param phone
     * @return
     */
    @GetMapping(value = "/getUserInfoByPhone/{phone}")
    public Ret getUserInfoByPhone(@PathVariable("phone")String phone){
        if(StringUtils.isBlank(phone)){
            return Ret.err(1,0,ExceptionEnum.PARAM_EXCEPTION.getName());
        }
        return Ret.success(this.userClientService.getUserInfoByPhone(phone));
    }

    /**
     *  用户登录
     * @param phone
     * @param password
     * @return
     */
    @PostMapping(value = "/login")
    public Ret login(String phone , String password){
        if(StringUtils.isBlank(phone)){
            return Ret.err(1,0,ExceptionEnum.PARAM_EXCEPTION.getName());
        }
        User user = this.userClientService.getUserInfoByPhone(phone);
        if( user == null){
            return Ret.err(1,0,ExceptionEnum.USER_NOT_EXCEPTION.getName());
        }
        if (!password.equals(user.getPassword())){
            return Ret.err(1,0,"输入的密码不正确!");
        }
        return new Ret(user);
    }

    /**
     *  用户注册
     * @param user
     * @return
     */
    @PostMapping(value = "/regUser")
    public Ret registerUser(@RequestBody User user){
        if(user == null){
            return Ret.err(1,0,ExceptionEnum.PARAM_EXCEPTION.getName());
        }
        return Ret.success(this.userClientService.regUser(user));
    }

    /**
     *  用户信息更新
     * @param user
     * @return
     */
    @PostMapping(value = "/updateUserInfo")
    public Ret updateUserInfo(@RequestBody User user){
        if(user == null){
            return Ret.err(1,0,ExceptionEnum.PARAM_EXCEPTION.getName());
        }
        return Ret.success(this.userClientService.updateUserInfo(user));
    }

}
