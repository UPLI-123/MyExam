package cn.lch.service;

import cn.lch.entity.Permission;
import cn.lch.entity.User;
import cn.lch.entity.vo.TableUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;
import java.util.Set;

/**
 * (User)表服务接口
 *
 * @author lch
 * @since 2022-04-09 19:33:26
 */
public interface UserService extends IService<User> {

    // 添加用户
    public int addUser(User user) ;

    // 根据用户名，对用户进行查询
    public User selectByUsername(String username) ;

    // 查询用户的权限
    public Set<Permission> findByIdRoot(Integer id) ;

    // 查询用户的子权限
    public Set<Permission> findByIdSubRoot(Integer id, Integer fatherId) ;

    public int updatePwd(User user) ;

    public IPage<User> findAllUser(Page page) ;

    // 删除用户的信息
    public void delUserInfo(Integer id) ;


    // 获得某个考试某一等级的全部信息
    public List<TableUser> getAllTableUser(String name, int examCode) ;

    // 根据主键 进行查询用户表
    public User findById(int id) ;
}

