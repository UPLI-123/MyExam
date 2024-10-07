package cn.lch.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * @projectName: exam
 * @package: com.exam.util
 * @className: RegexUtils
 * @author: lch
 * @description: 用来做正则表达式的判断
 * @date: 2022/4/10 10:15
 * @version: 1.0
 */
public class RegexUtils {

    public static boolean isPhone(String phone){
        return matches(phone, RegexPatterns.PHONE_REGEX);
    }

    public static boolean isEmail(String email){
        return matches(email, RegexPatterns.EMAIL_REGEX);
    }


    /**
     * @param str: 字符串
    	 * @param regex:  匹配规则
      * @return boolean
     * @author  lch
     * @description
     * @date  2022年4月10日10:18:54
     */
    private static boolean matches(String str, String regex){
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        return str.matches(regex);
    }
//    对静态方法进行测试
    public static void main(String[] args) {
        System.out.println(isEmail("1212"));
        System.out.println(isPhone("13792344723"));

    }

}
