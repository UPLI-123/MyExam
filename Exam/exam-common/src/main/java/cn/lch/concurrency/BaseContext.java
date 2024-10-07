package cn.lch.concurrency;

/**
 * @Author: LCH
 * @Description: 用于解决高并发的情况，由于是在本地进行存储
 * @Date: Create in 21:35 2024/8/10
 * @Modified By:
 */
public class BaseContext {

    // 将用户邮箱 进行存储，方便在后续的业务中使用
    public static ThreadLocal<String> thread_username = new ThreadLocal<>() ;

    // 用户用户注册
    public static ThreadLocal<String> thread_uuid = new ThreadLocal<>() ;

    // 将邮箱信息存储在线程变量中
    public static void setUsername(String username){
        thread_username.set(username);
    }

    // 获取存储在线程变量中的邮箱信息
    public static String getUsername(){
        return thread_username.get() ;
    }

    // 防止内存泄露, 移除在线程变量中的邮箱信息
    public static void removeUsername(){
        thread_username.remove();
    }

    public static void setUUID(String uuid){
        thread_uuid.set(uuid);
    }

    public static String getUUID(){
        return thread_uuid.get() ;
    }

    public static void removeUUID(){
        thread_uuid.remove();
    }


}
