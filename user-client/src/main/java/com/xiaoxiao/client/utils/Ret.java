package com.xiaoxiao.client.utils;

/**
 * ������Ӧ���
 * @author wdh
 * 2017-03-24 22:26:24
 */
public class Ret {

	//״状态码
	private Integer status ;

	//是否成功
	private Integer success ;
	
	//信息
	private String msg ;
	
	//数据
	private Object data ;

	/*
	 * getter setter
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	

	public Ret(Integer status, Integer success, String msg, Object data) {
		super();
		this.status = status;
		this.success = success;
		this.msg = msg;
		this.data = data;
	}


	public Ret() {
		super();
	}
	


	public Ret(Object data) {
		this.status = 1 ;
		this.success = 1 ;
		this.msg = "成功!";
		this.data = data;
	}
	
	public static Ret success(){
		return new Ret(null);
	}

	public static Ret success(Object data){
		return new Ret(data);
	}
	
	public static Ret err(Integer status,Integer success, String msg){
		return new Ret(status, success, msg, null);
	}
}
