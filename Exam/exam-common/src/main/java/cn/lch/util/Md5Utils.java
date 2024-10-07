package cn.lch.util;

import org.springframework.util.DigestUtils;

//进行 MD5 加密
public class Md5Utils {

	public static String  md5(String str){
		return DigestUtils.md5DigestAsHex(str.getBytes());
	}
}
