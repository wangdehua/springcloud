package com.xiaoxiao.douniwan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DouNiWanApplication {

    public static void main(String args[]){
        SpringApplication.run(DouNiWanApplication.class, args);
    }

}
