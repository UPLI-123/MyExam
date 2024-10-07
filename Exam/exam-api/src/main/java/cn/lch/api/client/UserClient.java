package cn.lch.api.client;

import cn.lch.api.entity.User;
import cn.lch.api.entity.vo.UserVo;
import cn.lch.util.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: LCH
 * @Description:
 * @Date: Create in 21:19 2024/10/6
 * @Modified By:
 */
@FeignClient("user-service")
public interface UserClient {

//    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
//    public ApiResult register(@RequestBody User user) ;
//
//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    public ApiResult login(@RequestBody UserVo user) ;


    @PostMapping("/selectByUsername")
    public User selectByUsername(String username) ;




}
