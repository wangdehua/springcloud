package com.xiaoxiao.xiaoqigui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class XiaoQiGuiApplication {
    public static void main(String args[]){
        SpringApplication.run(XiaoQiGuiApplication.class, args);
    }

}
