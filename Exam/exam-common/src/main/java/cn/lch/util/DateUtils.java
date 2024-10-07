package cn.lch.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: LCH
 * @Description: 用于将Date类型的数据转化为字符串，对date类型的数据进行处理
 * @Date: Create in 22:54 2024/8/16
 * @Modified By:
 */
public class DateUtils {

    // 将Date类型的数据转化为String类型的数据
    public static  String dateToString(Date date, String format){
        if(date == null){
            return null ;
        }
        // 进行格式的转化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date) ;
    }


}
