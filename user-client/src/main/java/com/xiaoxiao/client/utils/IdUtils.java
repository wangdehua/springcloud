package com.xiaoxiao.client.utils;


/**
 * Created by liangliang on 2017/04/27.
 *
 * @author liangliang
 * @since 2017/04/27
 */
public class IdUtils {

    public static String getId(Long tenantId, String bizId) {
        return String.format("%s:%s", tenantId, bizId);
    }
}
