package cn.lch.controller;


import cn.lch.entity.User;
import cn.lch.service.UserRoleService;
import com.baomidou.mybatisplus.extension.api.ApiController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.lch.util.ApiResult;
import cn.lch.util.ApiResultHandler;

/**
 * (UserRole)表控制层
 *
 * @author makejava
 * @since 2022-04-09 22:03:07
 */
@RestController
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService ;

    @ApiOperation("改变用户的角色")
    @RequestMapping(value = "/updateRole",method = RequestMethod.POST)
    public ApiResult updateRole(@RequestBody User user){
        try{
            userRoleService.updateRole(user) ;
            return ApiResultHandler.success() ;
        }catch (Exception e){
            return ApiResultHandler.buildApiResult(500,"",null)  ;
        }
    }

}

