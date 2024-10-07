package cn.lch.realm;


import cn.lch.api.client.UserClient;
import cn.lch.api.entity.User;
import cn.lch.concurrency.BaseContext;
import cn.lch.token.JwtToken;
import cn.lch.utils.JwtUntils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
// 自定义 realm

/***
 * 首先加入容器，方便 使用auto来进行自动装填
 */
@Component
public class MyRealm extends AuthorizingRealm {


    @Autowired
    UserClient userService ;
//     重写 support方法  ,用来 支持 自定义的token
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //    授权，给用户角色 进行授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.iterator().next();
        System.out.println(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo() ;
        return info;
    }
//    认证: 认证规则
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
////        获得token 对象
        JwtToken jwtToken  = (JwtToken) authenticationToken  ;
//        System.out.println(111);
//       获得token 字符串
        String jwt = (String) jwtToken.getPrincipal();
//        对jwt 进行解析
        Claims claims = JwtUntils.parseJwt(jwt);
//        获得用户的用户名
        String username = claims.getId();
//        判断一下用户名是否存在
        User user = userService.selectByUsername(username);
        System.out.println(user);
        if(user == null){
            return null ;
        }
//        System.out.println(Thread.currentThread());
        // 将创建的username 存入到
        BaseContext.setUsername(username);
        //创建返回值 对象
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,user.getPassword(),this.getName()) ;
        return simpleAuthenticationInfo;
    }
}
