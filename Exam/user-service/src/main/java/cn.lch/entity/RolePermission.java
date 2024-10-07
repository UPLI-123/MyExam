package cn.lch.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (RolePermission)表实体类
 *
 * @author makejava
 * @since 2022-04-09 22:03:09
 */
@SuppressWarnings("serial")
public class RolePermission extends Model<RolePermission> {

    private Integer roleId;

    private Integer permissionId;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }
}

