package cn.lch.utils;

/**
 * @projectName: exam
 * @package: com.exam.util
 * @className: RegexPatterns
 * @author: lch
 * @description: 一些正则表达式的规范
 * @date: 2022/4/10 10:16
 * @version: 1.0
 */
public class RegexPatterns {
    /**
     * 手机号正则
     */
    public static final String PHONE_REGEX = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
}
