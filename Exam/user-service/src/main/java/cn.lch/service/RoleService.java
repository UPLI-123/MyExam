package cn.lch.service;

import cn.lch.entity.Role;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

/**
 * (Role)表服务接口
 *
 * @author makejava
 * @since 2022-04-09 22:03:08
 */
public interface RoleService{

    IPage<Role> findAllrole(Page<Role> page) ;

    int add(Role role) ;

    int deleteRole(Integer id) ;

    List<Role> selectAllRole() ;

}

