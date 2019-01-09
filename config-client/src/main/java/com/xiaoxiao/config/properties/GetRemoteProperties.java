package com.xiaoxiao.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class GetRemoteProperties {

    @Value("${id}")
    private String id;

    @Value("${desc}")
    private String desc;

    @RequestMapping("/test")
    public String test() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "id:" + id + " desc:" + desc ;
    }
}
