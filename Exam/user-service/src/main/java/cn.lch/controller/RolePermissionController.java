package cn.lch.controller;


import cn.lch.service.RolePermissionService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.ApiController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.lch.util.ApiResult;
import cn.lch.util.ApiResultHandler;

/**
 * (RolePermission)表控制层
 *
 * @author makejava
 * @since 2022-04-09 22:03:09
 */
@RestController
public class RolePermissionController{

    @Autowired
    RolePermissionService rolePermissionService ;

    @ApiOperation("对用户的权限信息进行处理")
    @RequestMapping(value = "/dealRoleP",method = RequestMethod.POST)
    public ApiResult dealRoleP(Integer roleId, String roots){
        try{
//            System.out.println(roleId);
//            将前台 传过来的json字符串转化为int 型的数组
            JSONArray json = JSONObject.parseArray(roots);
//            创建一个 接收的数组
            Integer[] root = new Integer[json.size()] ;
//            json.stream().forEach(System.out::println);
            Integer [] arry = json.toArray(root) ;
//            从前端获得了数据 ，然后进行处理
            rolePermissionService.dealRoleP(roleId,arry) ;
            return ApiResultHandler.success() ;
        }catch (Exception e){
            return ApiResultHandler.buildApiResult(500,"失败",null) ;
        }
    }

}

