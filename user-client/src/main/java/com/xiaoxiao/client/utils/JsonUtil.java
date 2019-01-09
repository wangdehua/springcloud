package com.xiaoxiao.client.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonUtil {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 把object转为json
	 * @author wdh
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj) {
		if(obj == null) {
			return "";
		}
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 将json结果集转化为对象
     * @author wdh
     * @param jsonData json数据
     * @param clazz 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = mapper.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将json数据转换成pojo对象list
     * @author wdh
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = mapper.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
	
}
