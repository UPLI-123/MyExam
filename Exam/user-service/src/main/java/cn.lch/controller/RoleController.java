package cn.lch.controller;


import cn.lch.entity.Role;
import cn.lch.service.RoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.lch.util.ApiResult;
import cn.lch.util.ApiResultHandler;

import java.util.List;

/**
 * (Role)表控制层
 *
 * @author makejava
 * @since 2022-04-09 22:03:08
 */
@RestController
public class RoleController{

    @Autowired
    RoleService roleService ;

    @ApiOperation("角色管理")
    @RequestMapping(value = "/getRoleInfo",method = RequestMethod.POST)
    public ApiResult getRoleInfo(Integer cur, Integer size){
        try {
            Page<Role> page = new Page<>(cur,size) ;
            IPage<Role> allrole = roleService.findAllrole(page);
            return ApiResultHandler.success(allrole) ;
        }catch (Exception e){
            return ApiResultHandler.buildApiResult(500,"查询失败",null) ;
        }

    }

    @ApiOperation("增加角色信息")
    @RequestMapping(value = "/addRole" ,method = RequestMethod.POST)
    public ApiResult addRole(@RequestBody Role role) {
        try{
            roleService.add(role) ;
            return ApiResultHandler.success() ;
        }catch (Exception e){
            return ApiResultHandler.buildApiResult(500,"失败",null) ;
        }
    }

    @ApiOperation("删除角色信息")
    @RequestMapping(value = "/deleteRole/{roleId}",method = RequestMethod.POST)
    public ApiResult deleteRole(@PathVariable("roleId") Integer roldId) {
        try {
            roleService.deleteRole(roldId) ;
            return ApiResultHandler.success() ;
        }catch (Exception e){
            return ApiResultHandler.buildApiResult(500,"查找失败",null) ;
        }

    }

    @ApiOperation("查询所有的角色信息")
    @RequestMapping(value = "/getAllRole",method = RequestMethod.POST)
    public ApiResult getAllRole(){
        try{
            List<Role> roles = roleService.selectAllRole();
            return  ApiResultHandler.success(roles) ;
        }catch (Exception e){
            return ApiResultHandler.buildApiResult(500,"请求出错",null) ;
        }
    }

}

