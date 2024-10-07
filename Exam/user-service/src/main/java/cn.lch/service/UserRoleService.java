package cn.lch.service;


import cn.lch.entity.User;
import cn.lch.entity.UserRole;

/**
 * (UserRole)表服务接口
 *
 * @author makejava
 * @since 2022-04-09 22:03:08
 */
public interface UserRoleService  {

    int updateRole(User user) ;

    UserRole findByuid(Integer id) ;

}

