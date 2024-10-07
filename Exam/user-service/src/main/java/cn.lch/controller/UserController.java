package cn.lch.controller;

import cn.lch.concurrency.BaseContext;
import cn.lch.entity.Permission;
import cn.lch.entity.User;
import cn.lch.entity.UserRole;
import cn.lch.entity.vo.TableUser;
import cn.lch.entity.vo.UserVo;
import cn.lch.service.UserRoleService;
import cn.lch.service.UserService;
import cn.lch.token.JwtToken;
import cn.lch.utils.Base64;
import cn.lch.utils.JwtUntils;
import cn.lch.utils.RedisUtils;
import cn.lch.utils.RegexUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;
import cn.lch.util.ApiResult;
import cn.lch.util.ApiResultHandler;
import cn.lch.util.Md5Utils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * (User)表控制层
 *
 * @author lch
 * @since 2022-04-09 19:33:15
 */
@RestController
@CrossOrigin // 跨域处理
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    final UserService userService ;
//    final EmailInfoService emailInfoService  ;
    final UserRoleService userRoleService ;

    final RedisTemplate redisTemplate ;


    final Producer producer ;

    @ApiOperation("用户注册功能")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ApiResult register(@RequestBody User user){
//        System.out.println("register method - Thread ID: " + Thread.currentThread().getId());
        ApiResultHandler apiResultHandler = new ApiResultHandler();
//        String uuid = BaseContext.getUUID();
//        System.out.println(uuid);
        String key = "code:"+user.getUuid() ;
        System.out.println(key);
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        System.out.println(redisUtils.get(key));
        String code = (String) redisUtils.get(key);
        if(code == null){
            return ApiResultHandler.buildApiResult(500,"当前邮箱没有接受到验证码",null) ;
        }
        // 验证验证码是否正确
        if(!code.equals(user.getCode())){
            return ApiResultHandler.buildApiResult(500,"验证码不正确",null) ;
        }
        int tag = userService.addUser(user);
        // 添加成功后将资源进行回收
//        BaseContext.removeUUID();
        return ApiResultHandler.buildApiResult(200,"注册成功",null) ;
    }

    @ApiOperation("用户登录功能")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ApiResult login(@RequestBody UserVo user){
        ApiResultHandler apiResultHandler = new ApiResultHandler();
        // 对验证码信息进行验证
        String code = user.getCode();
        String uuid = user.getUuid();
        String key = "user:" + uuid ;
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        String code1 = String.valueOf(redisUtils.get(key));
        if(!code.equals(code1)){
            return ApiResultHandler.buildApiResult(500, "验证码不正确",null)  ;
        }
//        首先对用户名和密码进行验证 ,规定 使用 邮箱或者电话 进行登录
//        将邮箱和电话设置为统一个属性
        user.setTel(user.getEmail());
        if(user.getEmail() == null || user.getPassword() == null){
            return ApiResultHandler.buildApiResult(500,"账户和密码不能为空",null) ;
        }
//        将用户的输入的密码进行MD5 加密后，然后再放到token 中去
        user.setPassword(Md5Utils.md5(user.getPassword()));
//        获得subject对象 , 由于将SecurityUtils加入了容器所以可以在任何地方进行调用
//        创建 jwt
//         对传过来的用户名进行判断，用来确定 究竟是手机登录 还是邮箱登录
        String jwt ;
        if(RegexUtils.isPhone(user.getEmail())){
//            设置时效为 30分钟
            jwt = JwtUntils.cretateJwt(user.getEmail(), "tel", user.getPassword(), 1000 * 60 * 30);
        }else if(RegexUtils.isEmail(user.getEmail())){
            jwt = JwtUntils.cretateJwt(user.getEmail(),"email",user.getPassword(), 1000 * 60 * 30) ;
        }else{
            return ApiResultHandler.buildApiResult(500,"用户名不符合规范",null) ;
        }
//        创建 token
        JwtToken token = new JwtToken(jwt, user.getPassword());
        Subject subject = SecurityUtils.getSubject();
//        执行登录操作
        try{
            subject.login(token);
        }catch (UnknownAccountException e){
            return ApiResultHandler.buildApiResult(500,"账号不存在",null) ;

        }catch (IncorrectCredentialsException e){
            return ApiResultHandler.buildApiResult(500,"密码错误",null) ;
        }
//        如果上上述 判断都成立的话，那么代表登录成功，可以向前端返回一个 token 对象了
        User backUser = userService.selectByUsername(user.getEmail());
//        对返回的数据进行部分隐藏
        backUser.setPassword(null);
//        将要返回的数据进行封装
        Map<String,Object> map  = new HashMap<>() ;
        map.put("user",backUser) ;
        map.put("token",jwt)  ;
        // 对jwt 进行解析 将其存储在线程变量中
//        BaseContext.setEmail();
//        登录成功
        return ApiResultHandler.buildApiResult(200,"登录成功",map) ;
    }

    @ApiOperation("根据用户id查询用户权限操作")
    @RequestMapping(value = "/findRoot",method = RequestMethod.POST)
    public ApiResult findRoot(@RequestParam(value = "id",required = false) Integer id){
//        System.out.println(id);
        Set<Permission> roots = userService.findByIdRoot(id) ;
//         准备好向前台传输的数据的封装形式
        List<Map<String,Object>> list = new ArrayList<>() ;
        for (Permission root : roots) {
            Map<String,Object> map = new HashMap<>() ;
            map.put("menu",root) ;
            Set<Permission> subMenu = userService.findByIdSubRoot(id, root.getPermissionId());
            map.put("submenu",subMenu) ;
            list.add(map) ;
        }
        return ApiResultHandler.buildApiResult(200,"查询成功",list) ;
    }
//
//    @ApiOperation("找回密码操作")
//    @RequestMapping(value = "/forget", method = RequestMethod.POST)
//    public ApiResult forget(@RequestBody User user){ // 此处 一定要用requestbody 否则接收不到前端的数据
//
//        try {
////             首先 根据 邮箱和验证吗码进行查询
//            EmailInfo emailInfo = emailInfoService.selectEmailInfo(user.getEmail(), user.getCode());
//            if(emailInfo == null){
//                return ApiResultHandler.buildApiResult(500,"找回失败",null) ;
//            }else{
//                // 进行密码更新操作
//                int row = userService.updatePwd(user);
//                if(row > 0 ){
//                    emailInfoService.deleteEmail(emailInfo.getId())  ;
//                    return ApiResultHandler.buildApiResult(200,"找回成功",null) ;
//                }else{
//                    return ApiResultHandler.buildApiResult(500,"找回失败",null) ;
//                }
//            }
//        }catch (Exception e){
//            return ApiResultHandler.buildApiResult(500,"找回失败",null) ;
//        }
//    }

    @ApiOperation("查询所有用户的信息")
    @RequestMapping(value = "/users/{page}/{size}",method = RequestMethod.GET)
    public ApiResult users(@PathVariable Integer page,@PathVariable Integer size){
        try {
            Page<User> page1 = new Page<>(page,size) ;
            IPage<User> all = userService.findAllUser(page1);
            return ApiResultHandler.success(all) ;
        }catch (Exception e){
            return ApiResultHandler.buildApiResult(500,"请求失败",null) ;
        }
    }

    @ApiOperation("删除用户操作")
    @RequestMapping( value = "/delUser/{id}",method = RequestMethod.GET)
    public ApiResult delUser(@PathVariable Integer id){
        try{
            userService.delUserInfo(id);
            return ApiResultHandler.success() ;
        }catch (Exception e){
            return ApiResultHandler.buildApiResult(500,"",null) ;
        }
    }

    @ApiOperation("查询当前用户的权限")
    @RequestMapping(value = "/findOwnRoot",method = RequestMethod.POST)
    public ApiResult findOwnRoot(Integer id){
        try{
            UserRole ur = userRoleService.findByuid(id);
            return ApiResultHandler.success(ur) ;
        }catch (Exception e){
            return ApiResultHandler.buildApiResult(500,"请求失败",null) ;
        }
    }

    @GetMapping("/captchaImage")
    public ApiResult captchaImage(HttpServletResponse response){
        // 实现生成验证码的功能
        Map<String,Object> map = new HashMap<>() ;
        // 准备redis的key值
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        String key = "user:" + id ;
        map.put("uuid", id) ;
        // 放入 到redis中
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        // 设置返回到前端的类型
        response.setContentType("image/jpeg") ;
        // 生成文字的字符串
        String text = producer.createText();
        System.out.println("当前生成的字符串为："+text) ;
        // 个位数相加
        String s1 = text.substring(0, 2);
        String s2 = text.substring(2, 4);
        int count = Integer.valueOf(s1).intValue() + Integer.valueOf(s2).intValue() ; // intValue 是解包装操作
        redisUtils.set(key, count, 6000) ;
        // 生成图片验证码
        BufferedImage image = producer.createImage(s1 + "+" + s2 + "=?");
        try(FastByteArrayOutputStream sos = new FastByteArrayOutputStream()){ // try-source 写法
            // 将sos 转化为 base64 的形式传递到前端
            ImageIO.write(image,"jpg", sos) ;
            map.put("img", Base64.encode(sos.toByteArray()));
        } catch (IOException e) {
            return ApiResultHandler.buildApiResult(201, "验证生成失败",null) ;
        }
        return ApiResultHandler.buildApiResult(200,null, map) ;
    }

    @ApiOperation("获得等级的详细信息")
    @RequestMapping(value = "/getUserInfos",method = RequestMethod.POST)
    public ApiResult getUserInfos(String name, int examCode){
        try{
            List<TableUser> all = userService.getAllTableUser(name, examCode);
            return ApiResultHandler.success(all) ;
        }catch (Exception e){
            return ApiResultHandler.fail() ;
        }
    }

    @ApiOperation("获取用户的信息")
    @GetMapping("/getInfo")
    public ApiResult getInfo(){
        // 通过 ThreadLocal 来获取 相应的用户信息
//        System.out.println(Thread.currentThread());
        String username = BaseContext.getUsername();
        System.out.println(username);
//        将用户的姓名信息传递出去
        User user = userService.selectByUsername(username);
        User res = new User()  ;
        res.setId(user.getId());
        res.setName(user.getName()) ;
        return ApiResultHandler.buildApiResult(200, null, res) ;
    }

    @PostMapping("/selectByUsername")
    public User selectByUsername(String username){
        return userService.selectByUsername(username) ;
    };
}

