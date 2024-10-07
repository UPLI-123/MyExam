package cn.lch.config;


import cn.lch.filter.JwtFilter;
import cn.lch.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// 配置类 , 对shiro 框架 进行配置
@Configuration
public class ShiroConfig {

    @Autowired
    MyRealm myRealm ;

    //   1. 首先给shiro 框架  注入一个安全管理器
    @Bean
    public DefaultWebSecurityManager getWebDefaultSecurity(){
        //  先创建一个默认的安全管理器
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 安全管理器需要添加一个realm ,此处自定义创将realm
        defaultWebSecurityManager.setRealm(myRealm);
        defaultWebSecurityManager.setSubjectDAO(defaultSubjectDAO());
        return defaultWebSecurityManager ;
}

    //    先注入一个shiro拦截器
    @Bean
    public ShiroFilterFactoryBean getBean(DefaultWebSecurityManager manager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        添加安全管理器
        shiroFilterFactoryBean.setSecurityManager(manager);

        //配置过滤路径 此处要导入selvet 包
        Map<String, Filter> filterMap = new HashMap<>() ;
        filterMap.put("jwt",new JwtFilter()) ;
//        将过滤器加入
        shiroFilterFactoryBean.setFilters(filterMap);
        Map<String,String> map = new LinkedHashMap<>() ; //使用的链式的hashmap 所以规则是自上到下执行的

        //不拦截swagger
         map.put("/swagger-ui.html**", "anon");
         map.put("/v2/api-docs", "anon");
         map.put("/swagger-resources/**", "anon");
         map.put("/webjars/**", "anon");
//         将注册登录放行
        map.put("/user/register","anon") ;
        map.put("/user/login","anon") ;
        map.put("/user/captchaImage","anon") ;
//        将用户上传的静态志愿进行放行
        map.put("/upload/**", "anon") ;
//        将发送邮件功能进行放行
        map.put("/sendEmail","anon") ;
        map.put("/getUrl","anon") ;
//        将生成验证码功能进行
//         设置拦截路径
//         使所有请求都用jwt 来过滤
        map.put("/**","jwt") ;
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean ;
    }

//    开启 权限的注释
//    开启注解代理  ，如果没有以下两个方法的话，那么授权时的一些语句会报错
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 关闭shiro的session（无状态的方式使用shiro）,shiro 默认带缓存，
     * 所以第一次认证之后就不会触发认证了
     *
     * @return
     */
    private DefaultSubjectDAO defaultSubjectDAO() {
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        return subjectDAO;
    }

    // 添加比对器
    @Bean
    HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        return hashedCredentialsMatcher;
    }
}
