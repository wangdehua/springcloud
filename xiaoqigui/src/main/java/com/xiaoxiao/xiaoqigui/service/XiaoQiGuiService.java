package com.xiaoxiao.xiaoqigui.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoxiao.xiaoqigui.utils.HttpUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class XiaoQiGuiService {

    private Logger logger = Logger.getLogger(XiaoQiGuiService.class) ;

    @Value("${weather.key}")
    private String key;

    @Value("${weather.now_weather_url}")
    private String nowWeatherUrl;

    @Value("${weather.now_lifestyle_url}")
    private String nowLifestyleUrl;

    @Value("${weather.now_air_url}")
    private String nowAirUrl;

    @Value("${weather.auto_ip}")
    private String auto_ip;


    //获取今天天气信息
    public Map<String,Object> nowWeather(){
        Map<String,Object> ret = new HashMap<>();

        Map<String, String> param = new HashMap<>();
        param.put("key", key);
        param.put("location", auto_ip);
        JSONObject jsonObject = JSONObject.parseObject(HttpUtils.sendGet(nowWeatherUrl, param)).getJSONArray("HeWeather6").getJSONObject(0);
        String status = jsonObject.get("status").toString();
        if(status.equals("ok")){
            //获取地区
            String area = jsonObject.getJSONObject("basic").get("admin_area").toString();
            //当前日期状况
            JSONObject now = jsonObject.getJSONObject("now");
            //实况天气状况描述
            String cond_txt = now.get("cond_txt").toString();
            //	体感温度，默认单位：摄氏度
            String fl = now.get("fl").toString();
            //风向
            String wind_dir = now.get("wind_dir").toString();
            //风力
            String wind_sc = now.get("wind_sc").toString();

            //拼接天气情况
            ret.put("area",area);
            ret.put("cond_txt",cond_txt);
            ret.put("fl",fl);
            ret.put("wind_dir",wind_dir);
            ret.put("wind_sc",wind_sc);
            ret.put("status","ok");
        }else{
            ret.put("status","no ok!");
        }
        return ret ;
    }

    //获取今天的生活指数
    public Map<String,Object> lifeStyle(){
        Map<String,Object> ret = new HashMap<>();

        Map<String, String> param = new HashMap<>();
        param.put("key", key);
        param.put("location", auto_ip);
        JSONObject jsonObject = JSONObject.parseObject(HttpUtils.sendGet(nowLifestyleUrl, param)).getJSONArray("HeWeather6").getJSONObject(0);
        String status = jsonObject.get("status").toString();
        if(status.equals("ok")){
            JSONArray lifestyle = jsonObject.getJSONArray("lifestyle");
            for (Object o : lifestyle) {
                Map<String,Object> map = (Map) o ;
                if(map.get("type").toString().equals("comf")){
                    //体感舒适度
                    String comf = map.get("txt").toString() ;
                    ret.put("comf",comf.substring(0,comf.length()-1));
                }
                if (map.get("type").toString().equals("drsg")){
                    //穿衣指数
                    String drsg = map.get("txt").toString();
                    ret.put("drsg",drsg.substring(0,drsg.length()-1));
                }
                if (map.get("type").toString().equals("flu") && !map.get("brf").toString().equals("少发")){
                    //感冒指数
                    ret.put("flu",map.get("brf")+"感冒，注意多喝热水哦！");
                }
                if(map.get("type").toString().equals("sport") && !map.get("brf").toString().equals("较不宜")){
                    //运动指数
                    ret.put("sport","今天天气不错，抓紧时间出去浪吧！");
                }
            }
            ret.put("status","ok");
        }else{
            ret.put("status","no ok!");
        }
        return ret ;
    }

    //获取今天的空气质量
    public Map<String,Object> nowAir(){
        Map<String,Object> ret = new HashMap<>();

        Map<String, String> param = new HashMap<>();
        param.put("key", key);
        param.put("location", auto_ip);
        JSONObject jsonObject = JSONObject.parseObject(HttpUtils.sendGet(nowAirUrl, param)).getJSONArray("HeWeather6").getJSONObject(0);
        String status = jsonObject.get("status").toString();
        if(status.equals("ok")){
            JSONObject air_now_city = jsonObject.getJSONObject("air_now_city");
            //获取空气质量
            String qlty = air_now_city.get("qlty").toString();
            ret.put("status","ok");
            if(qlty.equals("优") || qlty.equals("良") || qlty.equals("轻度污染")){
                ret.put("qlty_content","空气质量为"+qlty+"，不需要戴口罩");
            }else{
                ret.put("qlty_content","空气质量为"+qlty+"，需要戴口罩");
            }
        }else{
            ret.put("status","no ok!");
        }
        return ret ;
    }

    public Map<String,Object> getWeather() {
        Map<String,Object> ret = new HashMap<>(2);
        try {
            String content = "" ;
            //今天天气
            Map<String, Object> nowWeather = this.nowWeather();
            //今天生活指数
            Map<String, Object> lifeStyle = this.lifeStyle();
            //今天的空气质量
            Map<String, Object> nowAir = this.nowAir();
            if(nowWeather.get("status").toString().equals("ok") && lifeStyle.get("status").toString().equals("ok") && nowAir.get("status").toString().equals("ok")){
                //拼装天气信息
                content = nowWeather.get("area") +"今天的天气是" + nowWeather.get("cond_txt") + "，体感温度" +  nowWeather.get("fl") + "摄氏度，"
                        +  nowWeather.get("wind_dir") + nowWeather.get("wind_sc") + "级，" + lifeStyle.get("comf") + "，" + lifeStyle.get("drsg") +"，"
                        +  nowAir.get("qlty_content") ;
                if(lifeStyle.get("flu") != null){
                    content += "，" + lifeStyle.get("flu") ;
                }
                if (lifeStyle.get("sport") != null){
                    content += lifeStyle.get("sport") ;
                }
                ret.put("status","ok");
                ret.put("message",content);
            }else{
                ret.put("status","no ok!");
                ret.put("message","(⊙﹏⊙)出问题了...稍等...");
            }
        } catch (Exception e) {
            ret.put("status","no ok!");
            ret.put("message","(⊙﹏⊙)出问题了...稍等...");
        }
        return ret ;
    }
}
