package cn.lch.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Role)表实体类
 *
 * @author makejava
 * @since 2022-04-09 22:03:08
 */
@SuppressWarnings("serial")
@Data
public class Role extends Model<Role> {

    private Integer roleId;

    private String roleName;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

