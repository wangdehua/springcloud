package com.xiaoxiao.xiaoqigui.controller;

import com.xiaoxiao.xiaoqigui.service.XiaoQiGuiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/xiaoqigui")
public class XiaoQiGuiApiController {

    @Autowired
    private XiaoQiGuiService xiaoQiGuiService ;

    @RequestMapping(value = "/get")
    public Object getWeather(){
        return this.xiaoQiGuiService.getWeather();
    }

}
