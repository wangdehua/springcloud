package com.xiaoxiao.target.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaoxiao.common.utils.RedisUtils;
import com.xiaoxiao.target.utils.DateUtil;
import com.xiaoxiao.target.vo.MyTarget;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class TargetService {

    @Autowired
    private RedisUtils redisUtils ;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public Map<String,Object> addTarget(MyTarget myTarget){
        Map<String,Object> ret = new HashMap<>(2);
        try{
            if(StringUtils.isBlank(myTarget.getEmile()) ||
                    StringUtils.isBlank(myTarget.getEndDate()) ||
                    StringUtils.isBlank(myTarget.getContent()) ||
                    StringUtils.isBlank(myTarget.getType())){
                ret.put("status","no ok");
                ret.put("message","你少填了什么....");
            }else if(!isEmail(myTarget.getEmile())){
                ret.put("status","no ok");
                ret.put("message","邮箱格式不正确....");
            }else{
                myTarget.setStatus(1);
                myTarget.setCreateDate(sdf.format(new Date()));
                myTarget.setCreateDate(sdf.format(new Date()));
                if(StringUtils.isBlank(myTarget.getType())){
                    myTarget.setType("nomal");
                }
                if(this.redisUtils.hset(myTarget.getEmile(),myTarget.getType(),JSON.toJSONString(myTarget))){
                    ret.put("status","ok");
                    ret.put("message","目标已添加,奋斗吧!");
                }else{
                    ret.put("status","no ok");
                    ret.put("message","不好意思,服务器挂了....请稍后重试!");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            ret.put("status","no ok!");
            ret.put("message","服务器异常...");
        }
        return ret ;
    }

    public Map<String,Object> deleteTarget(MyTarget myTarget){
        Map<String,Object> ret = new HashMap<>();
        try{
            if(StringUtils.isBlank(myTarget.getEmile()) ||
                    StringUtils.isBlank(myTarget.getType())) {
                ret.put("status", "no ok");
                ret.put("message", "要删除的目标有问题....");
            }else if(!isEmail(myTarget.getEmile())){
                ret.put("status","no ok");
                ret.put("message","邮箱格式不正确....");
            } else {
                if(this.redisUtils.hdel(myTarget.getEmile(),myTarget.getType())){
                    ret.put("status", "ok");
                    ret.put("message", "目标删除成功....你怕啥完成不了吧,哈哈哈!");
                }else{
                    ret.put("status", "no ok");
                    ret.put("message", "服务器挂了....请稍后重试!");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            ret.put("status","no ok!");
            ret.put("message","服务器异常...");
        }
        return ret ;
    }

    public Map<String,Object> getMyTarget(String emile){
        Map<String,Object> ret = new HashMap<>() ;
        List<Map<String,Object>> list = new ArrayList<>();
        try{
            if(StringUtils.isBlank(emile)){
                ret.put("status", "no ok");
                ret.put("message", "你的邮箱有问题....");
            }else if(!isEmail(emile)){
                ret.put("status","no ok");
                ret.put("message","邮箱格式不正确....");
            } else {
                if(!this.redisUtils.hasKey(emile)){
                    ret.put("status", "no ok");
                    ret.put("message", "你确定你有目标 ? ");
                }else{
                    Map<Object, Object> map = this.redisUtils.hmget(emile);
                    Collection<Object> values = map.values();
                    for (Object value : values) {
                        Map<String,Object> targetMap = new HashMap<>();
                        MyTarget myTarget = JSONObject.parseObject(value.toString() , MyTarget.class);
                        targetMap.put("content",myTarget.getContent());
                        targetMap.put("createDate",myTarget.getCreateDate());
                        targetMap.put("endDate",myTarget.getEndDate());
                        targetMap.put("type",myTarget.getType());
                        if (sdf.parse(myTarget.getEndDate()).getTime() < new Date().getTime()){
                            targetMap.put("status","已完成");
                        }else{
                            targetMap.put("status","进行中");
                            //目标进行了多久
                            String loadingTime = DateUtil.distinceEndTime(myTarget.getCreateDate(), sdf.format(new Date()),1);
                            //还有多久完成
                            String howLong = DateUtil.distinceEndTime(sdf.format(new Date()), myTarget.getEndDate(),0);
                            targetMap.put("loadingTime",loadingTime);
                            targetMap.put("howLong",howLong) ;
                        }
                        list.add(targetMap);
                    }
                    ret.put("status","ok");
                    ret.put("message",list);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            ret.put("status","no ok");
            ret.put("message","服务器异常.....");
        }
        return ret ;
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
}
