package cn.lch.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.lch.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Role)表数据库访问层
 *
 * @author lch
 * @since 2022-04-09 22:03:08
 */
@Mapper
public interface RoleMapper  {

    @Select("select * from role")
    IPage<Role> findAll(Page<Role> page) ;

    @Insert("INSERT INTO role(role_id,role_name) VALUES(null,#{roleName}) ")
    Integer addRole(Role role) ;

    @Delete("DELETE FROM role where role_id = #{v1}")
    public Integer deleteRole(Integer id) ;

    @Select("select * from role")
    List<Role> selectAll() ;


}

