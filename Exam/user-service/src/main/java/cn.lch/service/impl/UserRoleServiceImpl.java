package cn.lch.service.impl;

import cn.lch.entity.User;
import cn.lch.entity.UserRole;
import cn.lch.mapper.UserRoleMapper;
import cn.lch.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (UserRole)表服务实现类
 *
 * @author makejava
 * @since 2022-04-09 22:03:08
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper ;

    @Override
    public int updateRole(User user) {
        return userRoleMapper.updateUserRole(user.getRoleId(),user.getId());
    }

    @Override
    public UserRole findByuid(Integer id) {
        return userRoleMapper.findByUid(id);
    }
}

