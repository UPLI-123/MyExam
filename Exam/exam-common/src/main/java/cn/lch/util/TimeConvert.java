package cn.lch.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeConvert {

	//  日期格式 转化
	public static String convert(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String t = null ; 
		if (time !=null) {
		 try {
			t = sdf.format(time) ;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		}
		return t ;
	}


//	 获得第几天前的日期
	public static Date getdate(int i) // 获得 第i 田的 时间
	 {
		Date dat = null;
		i = -i  ; 
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DATE, i);
		dat = cd.getTime();
		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp date = Timestamp.valueOf(dformat.format(dat));
		return date ;
	 }

}
