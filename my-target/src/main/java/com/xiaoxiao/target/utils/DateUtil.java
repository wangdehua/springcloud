package com.xiaoxiao.target.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 计算两个日期的时间差
     * @param startDate
     * @param endDate
     * @param type
     *              type = 1 返回字符串 目标开始了多久  ,  type = 0 目标还有多久完成
     * @return
     */
    public static String distinceEndTime(String startDate , String endDate , int type){
        String retMessage = "" ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //开始时间
            Date start = sdf.parse(startDate);
            //截止时间
            Date end = sdf.parse(endDate);
            //两时间差,精确到毫秒
            long diff = end.getTime() - start.getTime();
            long day = diff / 86400000;                         //以天数为单位取整
            long hour= diff % 86400000 / 3600000;               //以小时为单位取整
            long min = diff % 86400000 % 3600000 / 60000;       //以分钟为单位取整
            long seconds = diff % 86400000 % 3600000 % 60000 / 1000;   //以秒为单位取整
            //天时分秒
            if(type == 1){
                retMessage = "目标已经进行了:" + day + "天" + hour + "小时" + min + "分" + seconds + "秒" ;
            }else{
                retMessage = "距离目标完成还有:" + day + "天" + hour + "小时" + min + "分" + seconds + "秒" ;
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMessage = "" ;
        }
        return retMessage ;
    }

}
