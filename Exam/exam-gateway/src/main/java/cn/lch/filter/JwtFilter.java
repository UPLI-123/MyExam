package cn.lch.filter;

import cn.lch.token.JwtToken;
import cn.lch.utils.JwtUntils;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import cn.lch.util.ApiResultHandler;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


// 配置 jwt 消息拦截器
public class JwtFilter extends AuthenticatingFilter {
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
       HttpServletRequest request =  (HttpServletRequest) servletRequest ;
//     前端 除了的登录、注册 都需要从header中获得一个 Authorization， 用来判断是否已授权
        String jwt = request.getHeader("Authorization") ;
//        System.out.println(jwt);

//         如果没有 jwt 的话，代表当前 页面没有进行授权
        if(StringUtils.isEmpty(jwt)){
            return null ;
        }else{
//          如果有数据的话，转化为AauthenticationToken 的形式
//            对jwt 进行解析
            Claims claims = JwtUntils.parseJwt(jwt);
            return new JwtToken(jwt, claims.getSubject()) ;
        }
    }

//     主要拦截的部分
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
    HttpServletRequest request = (HttpServletRequest) servletRequest ;
//         获得 jwt
    String jwt = request.getHeader("Authorization");
    System.out.println(jwt);
    if("".equals(jwt) || jwt  == null ){
        return false ;
    }else{
//             检验jwt 的正确性
        Claims claims = JwtUntils.parseJwt(jwt);
//             如果不存在 jwt  或者  超出有效期的话
        if(claims == null || JwtUntils.isTokenExpired(claims.getExpiration())){
            HttpServletResponse response = (HttpServletResponse) servletResponse ;
//                 防止向前端传输数据时出现乱码情况
            response.setContentType("application/plain;charset=utf-8") ;
//                将前端信息以json 字符串的形式传递到前端
            PrintWriter writer = response.getWriter();
//                 401 代表没有权限访问
            writer.write(JSON.toJSONString(new ApiResultHandler().buildApiResult(401,"没有权限访问",null)));
//                不给放行
            return false;
        }

//            执行登录操作
//        System.out.println(executeLogin(servletRequest, servletRequest));
        return executeLogin(servletRequest,servletResponse) ;
    }
}

    //     此处代码是 跨域处理代码
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            httpServletResponse.setStatus(200);
            return false;
        }
//        System.out.println();
        return super.preHandle(request, response);
    }
}
