package com.xiaoxiao.target.scheduled;

import com.alibaba.fastjson.JSONObject;
import com.xiaoxiao.target.utils.DateUtil;
import com.xiaoxiao.target.utils.RedisUtils;
import com.xiaoxiao.target.vo.MyTarget;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Component
public class TargetTask {

    private Logger logger = Logger.getLogger(TargetTask.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;

    @Autowired
    private RedisUtils redisUtils ;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 定时任务  定时推送目标完成状况
     */
    @Scheduled(cron = "0 0 6 * * ?") //每天早上6点触发
    public void sendQQEmile(){
        try {
            Set<String> keys = this.redisUtils.keys();
            if(keys == null || keys.size() <= 0){
                logger.info("-----------------没有要发送的邮件----------------");
            }else{
                for (String key : keys) {
                    Map<Object, Object> map = this.redisUtils.hmget(key);
                    Collection<Object> values = map.values();
                    for (Object value : values) {
                        MyTarget myTarget = JSONObject.parseObject(value.toString() , MyTarget.class);
                        if (myTarget.getStatus() != 1){
                            //邮箱未认证
                            continue ;
                        }
                        if (sdf.parse(myTarget.getEndDate()).getTime() < new Date().getTime()){
                            //已经过期
                            continue ;
                        }else{
                            //目标进行了多久
                            String loadingTime = DateUtil.distinceEndTime(myTarget.getCreateDate(), sdf.format(new Date()),1);
                            //还有多久完成
                            String howLong = DateUtil.distinceEndTime(sdf.format(new Date()), myTarget.getEndDate(),0);
                            sendEmile(myTarget.getEmile(),this.getContent(myTarget.getContent(),loadingTime,howLong));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 邮件发送
     * @param user
     * @param content
     */
    public void sendEmile(String user,String content){
        try{
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom("1509258936@qq.com");//设置发信人，发信人需要和spring.mail.username配置的一样否则报错
            message.setTo(user);				//设置收信人
            message.setSubject("目标达成计划");	//设置主题
            message.setText(content,true);  	//第二个参数true表示使用HTML语言来编写邮件
            this.mailSender.send(mimeMessage);
            logger.info("-----------------定时邮件发送成功-------------------");
        }catch (Exception e){
            logger.info("-----------------定时邮件发送异常------------------"+e.getMessage());
            logger.info("-----------------邮件发送异常时间------------------"+sdf.format(new Date()));
            e.printStackTrace();
        }
    }

    public String getContent(String content , String loadingTime , String howLong ){
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>目标达成计划</title>"
                + "<meta name=\"content-type\" content=\"text/html; charset=UTF-8\">"
                + "</head>"
                + "<body>"
                + "<p style=\"color:#00ff00\">Good morning ! 亲 ~ ~ ~ </p>"
                +"\t\t<p style=\"color:#FF3E96\">       您的 <font color='#c00'>"+content+"<font> 的目标</p>"
                +"\t\t\t\t<p style=\"color:#c00\">              "+loadingTime+"</p>"
                +"\t\t\t\t<p style=\"color:#c00\">              "+howLong+"</p>"
                +"<p style=\"color:#9400D3\">奋斗吧 ! 少年 !</p>"
                + "</body>"
                + "</html>"; // 可以用HTMl语言写
    }

}
