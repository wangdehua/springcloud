package com.xiaoxiao.target.controller;

import com.xiaoxiao.target.service.TargetService;
import com.xiaoxiao.target.vo.MyTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/target")
public class TargetControloler {

    @Autowired
    private TargetService targetService ;

    /**
     * 添加目标
     * @param myTarget
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Object addtarget(MyTarget myTarget){
        return this.targetService.addTarget(myTarget) ;
    }

    /**
     * 删除目标
     * @param myTarget
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteTarget(MyTarget myTarget){
        return this.targetService.deleteTarget(myTarget);
    }

    /**
     * 获取我的目标
     * @param emile
     * @return
     */
    @RequestMapping(value = "/get")
    @ResponseBody
    public Object getMyTarget(@RequestParam(value = "emile" ,required = true)String emile){
        return this.targetService.getMyTarget(emile) ;
    }

}
