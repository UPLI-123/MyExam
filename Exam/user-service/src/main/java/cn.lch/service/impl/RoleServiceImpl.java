package cn.lch.service.impl;

import cn.lch.entity.Role;
import cn.lch.entity.User;
import cn.lch.mapper.RoleMapper;
import cn.lch.mapper.UserMapper;
import cn.lch.mapper.UserRoleMapper;
import cn.lch.service.RoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2022-04-09 22:03:08
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper ;
    @Autowired
    UserRoleMapper userRoleMapper  ;
    @Autowired
    UserMapper userMapper ;


    @Override
    public IPage<Role> findAllrole(Page<Role> page) {
        return roleMapper.findAll(page);
    }

    @Override
    public int add(Role role) {
        return roleMapper.addRole(role);
    }

    @Override
    @Transactional
    public int deleteRole(Integer id) {
//         删除了 角色 后 ，然后 删除相同角色的 用户
        Integer l1 = roleMapper.deleteRole(id);
//        首先查找到相应的user
        List<User> users = userRoleMapper.findUsers(id);
//        进行删除操作
        userRoleMapper.delByRid(id);
        for(User u:users){
            userMapper.delUserById(u.getId());
        }
        return l1 ;
    }

    @Override
    public List<Role> selectAllRole() {
        return roleMapper.selectAll();
    }
}

