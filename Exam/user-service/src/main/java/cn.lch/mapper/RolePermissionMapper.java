package cn.lch.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (RolePermission)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-09 22:03:09
 */
@Mapper
public interface RolePermissionMapper{

    @Delete("DELETE from role_permission where role_id = #{v1}")
    void delById(Integer id) ;

    @Insert("INSERT into role_permission (role_id,permission_id) VALUES(#{v1},#{v2})")
    int addRoleP(@Param("v1") Integer roleId, @Param("v2") Integer pid) ;



}

