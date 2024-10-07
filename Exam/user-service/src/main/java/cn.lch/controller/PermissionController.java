package cn.lch.controller;


import cn.lch.service.PermissionService;
import com.baomidou.mybatisplus.extension.api.ApiController;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.lch.util.ApiResult;
import cn.lch.util.ApiResultHandler;

import java.util.List;
import java.util.Map;

/**
 * (Permission)表控制层
 *
 * @author makejava
 * @since 2022-04-09 22:03:09
 */
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService ;

    @ApiOperation("获得树形的权限管理")
    @RequestMapping(value = "/getAllPermission", method = RequestMethod.GET)
    public ApiResult getAllPermission(){

        try{
            List<Map<String, Object>> tree = permissionService.findTree();
            return  ApiResultHandler.success(tree) ;
        }catch (Exception e){
            return ApiResultHandler.buildApiResult(500,"查询失败",null) ;
        }
    }

    @ApiOperation("树形主键初始化方法")
        @RequestMapping(value = "/initTree",method = RequestMethod.POST)
    public ApiResult initTree(Integer id){
        try{
            // todo 初始化方法
            Map<String, Object> map = permissionService.initTree(id);
            return ApiResultHandler.success(map) ;
        }catch (Exception e){
            return ApiResultHandler.buildApiResult(500,"初始化失败",null) ;
        }

    }


}

