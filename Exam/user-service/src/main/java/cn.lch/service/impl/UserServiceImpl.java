package cn.lch.service.impl;

import cn.lch.entity.Permission;
import cn.lch.entity.User;
import cn.lch.entity.vo.TableUser;
import cn.lch.mapper.PermissionMapper;
import cn.lch.mapper.UserMapper;
import cn.lch.mapper.UserRoleMapper;
import cn.lch.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.lch.util.Md5Utils;

import java.util.List;
import java.util.Set;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-04-09 19:33:26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper ;
    @Autowired
    UserRoleMapper userRoleMapper  ;

    @Autowired
    PermissionMapper permissionMapper  ;

    @Override
    @Transactional // 保证事务的一致性
    public int addUser(User user) {
//        要对传入的密码进行加密处理
//        对密码使用MD5进行加密
        String password = user.getPassword() ;
        String md5Pwd = Md5Utils.md5(password);
        user.setPassword(md5Pwd);
        int l1 = userMapper.addUser(user);
        Integer roleId = user.getRoleId();
        int l2 = 0 ;
        if(roleId!=null){
            l2 = userRoleMapper.addUserRole(roleId,user.getId());
        }else{
            l2 = userRoleMapper.addUserRole(1,user.getId());
        }
        return (l1 & l2) ;
    }
    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public Set<Permission> findByIdRoot(Integer id) {
        return permissionMapper.findByIdRoot(id);
    }

    @Override
    public Set<Permission> findByIdSubRoot(Integer id, Integer fatherId) {
        return permissionMapper.findByIdSubRoot(id,fatherId);
    }

    @Override
    public int updatePwd(User user) {
//         对传过来的密码进行md5 加密
        String pwd = Md5Utils.md5(user.getPassword());
        user.setPassword(pwd);
        user.setPassword(pwd);
        return userMapper.updatePwd(user);
    }

    @Override
    public IPage<User> findAllUser(Page page) {
        return userMapper.selectAll(page);
    }

    @Override
    @Transactional
    public void delUserInfo(Integer id) {
//        删除分为两步分进行 一个是删除 user表 一个是删除user-role 表
        userMapper.delUserById(id);
//        然后删除user-role 表 根据userId 进行删除
        userRoleMapper.delByUid(id) ;
    }

    @Override
    public List<TableUser> getAllTableUser(String name, int examCode) {
        if("A".equals(name)){
            return userMapper.getAllTableUserA(examCode);
        }else if("B".equals(name)){
            return userMapper.getAllTableUserB(examCode);

        }else if("C".equals(name)){
            return userMapper.getAllTableUserC(examCode);

        }else if("D".equals(name)){
            return userMapper.getAllTableUserD(examCode);
        }else{
            return userMapper.getAllTableUserE(examCode);
        }
    }

    // 根据主键 进行查询
    @Override
    public User findById(int id) {
        User user = lambdaQuery()
                .eq(User::getId, id)
                .one();
        return user;
    }
}

