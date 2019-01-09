package com.xiaoxiao.douniwan.service;

import com.alibaba.fastjson.JSONObject;
import com.xiaoxiao.douniwan.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DouNiWanService {

    @Autowired
    private RedisUtils redisUtils ;

    @Value("${douniwan.key}")
    private String key ;

    @Value("${douniwan.play_word_url}")
    private String url ;

    //每次请求的笑话条数
    private static final int PAGE_SIZE = 1 ;

    public Map<String,Object> getPlayWord(){
        Map<String,Object> ret = new HashMap<>() ;
        try{
            Map<String,String> param = new HashMap<>();
            long page = redisUtils.incr("page", 1);
            param.put("page", page + "" );
            param.put("pagesize",PAGE_SIZE + "");
            param.put("key",key);
            String res = HttpUtils.sendGet(url, param);
            JSONObject jsonObject = JSONObject.parseObject(res);
            if(jsonObject.get("error_code").toString().equals("0")){
                //成功
                String content = jsonObject.getJSONObject("result").getJSONArray("data").getJSONObject(0).getString("content");
                ret.put("message",content.replace("&nbsp;",""));
                ret.put("status","ok");
            }else{
                //失败
                ret.put("message","第三方服务异常!");
                ret.put("status","no ok");
            }
        }catch (Exception e){
            e.printStackTrace();
            //失败
            ret.put("message","后台服务异常!");
            ret.put("status","no ok");
        }
        return  ret ;
    }
}
