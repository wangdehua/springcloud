package com.xiaoxiao.client.service;

import com.xiaoxiao.client.pojo.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.Request;

//@FeignClient(name="user-server",fallback = UserClientServiceCallBack.class)
@FeignClient(name="user-server")
public interface UserClientService {

    /*
        1 . findById方法是服务提供者中的方法
        2 . @GetMapping不支持
        3 . @PathVariable得设置value
        4 . 只要参数是复杂对象，即使指定了是GET方法，feign依然会以POST方法进行发送请求。

     */
    @RequestMapping(value = "/user/getUserInfoById/{userId}", method = RequestMethod.GET)
    public User getUserInfoByUserId(@PathVariable("userId") Integer userId);


    @RequestMapping(value = "/user/getUserInfoByPhone/{phone}" , method = RequestMethod.GET)
    public User getUserInfoByPhone(@PathVariable("phone") String phone);

    @RequestMapping(value = "/user/reg" , method = RequestMethod.POST)
    public boolean regUser(@RequestBody User user);

    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    public boolean updateUserInfo(@RequestBody User user);
}
