package cn.lch.mapper;


import cn.lch.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * (Permission)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-09 22:03:09
 */
@Mapper
public interface PermissionMapper {

    /**
     * @param id:  用户id
      * @return Set<Permission>
     * @author lch
     * @description 根据用户id 来查找用户父权限
     * @date  2022年4月10日21:54:28
     */
    @Select("SELECT p.* from \n" +
            "role_permission rp , permission p ,role r, `user` u , user_role ur\n" +
            "where ur.user_id = u.id and ur.role_id = r.role_id and rp.role_id = r.role_id\n" +
            "and rp.permission_id = p.permission_id and u.id = #{v} and p.father_id = 0")
    public Set<Permission> findByIdRoot(Integer id) ;


    @Select("SELECT p.* from \n" +
            "role_permission rp , permission p ,role r, `user` u , user_role ur\n" +
            "where ur.user_id = u.id and ur.role_id = r.role_id and rp.role_id = r.role_id\n" +
            "and rp.permission_id = p.permission_id and u.id = #{v1} and p.father_id = #{v2}")
    public Set<Permission> findByIdSubRoot(@Param("v1") Integer id, @Param("v2") Integer fatherId) ;


    @Select("SELECT p.* from  permission p \n" +
            "where  p.father_id = 0")
    public Set<Permission> getAllRoot() ;

    @Select("SELECT p.* from  permission p \n" +
            "            where  p.father_id = #{v1}")
    public Set<Permission> getChildren(Integer id) ;

    @Select("SELECT p.* from  \n" +
            "            role_permission rp , permission p ,role r\n" +
            "            where  rp.role_id = r.role_id\n" +
            "            and rp.permission_id = p.permission_id and p.father_id = #{v1} and r.role_id =#{v2}")
    public Set<Permission> getAllP(@Param("v1")Integer id,@Param("v2") Integer id2) ;

    @Select("SELECT *from permission where permission.permission_id =#{v1}")
    public Permission findByID(Integer id) ;

}

