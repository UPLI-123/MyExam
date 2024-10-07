package cn.lch.service.impl;


import cn.lch.entity.Permission;
import cn.lch.mapper.PermissionMapper;
import cn.lch.mapper.RolePermissionMapper;
import cn.lch.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * (RolePermission)表服务实现类
 *
 * @author makejava
 * @since 2022-04-09 22:03:10
 */
@Service
public class RolePermissionServiceImpl  implements RolePermissionService {

    @Autowired
    RolePermissionMapper rolePermissionMapper ;
    @Autowired
    PermissionMapper permissionMapper ;

    @Override
    @Transactional
    public boolean dealRoleP(Integer roleId, Integer[] arry) {
        try {
            //  主要步骤分为两步走，第一步是删除 原来的权限信息，第二步是 添加新的权限信息
            rolePermissionMapper.delById(roleId) ;
//            遍历子信息从时候 同时获得父信息
            Set<Integer> father = new HashSet<>() ; // set 中不允许有重复的元素
            for(Integer a : arry){
                Permission byID = permissionMapper.findByID(a);
                father.add(byID.getFatherId()) ;
                rolePermissionMapper.addRoleP(roleId,a) ;
            }
            // 向数据库中添加父消息
            for(Integer b: father){
                rolePermissionMapper.addRoleP(roleId,b) ;
            }
            return true ;
        }catch (Exception e){

            return false;
        }
    }
}

