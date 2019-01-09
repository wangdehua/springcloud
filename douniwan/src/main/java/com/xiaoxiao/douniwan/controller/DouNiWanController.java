package com.xiaoxiao.douniwan.controller;

import com.xiaoxiao.douniwan.service.DouNiWanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/douniwan")
public class DouNiWanController {

    @Autowired
    private DouNiWanService douNiWanService ;

    @RequestMapping(value = "/get")
    public Object getPlayWord(){
        return this.douNiWanService.getPlayWord() ;
    }

}
