package com.xiaoxiao.client.utils;

public enum ExceptionEnum {

    PARAM_EXCEPTION("参数错误!",1000),

    USER_NOT_EXCEPTION("用户不存在!",1001),

    ;




    private String name ;

    private Integer code ;

    ExceptionEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
