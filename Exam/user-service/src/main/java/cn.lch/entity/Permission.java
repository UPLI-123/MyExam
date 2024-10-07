package cn.lch.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Permission)表实体类
 *
 * @author makejava
 * @since 2022-04-09 22:03:09
 */
@SuppressWarnings("serial")
@Data
public class Permission extends Model<Permission> {

    private Integer permissionId;

    private String permissionCode;

    private String permissionName;

    private Integer fatherId;

    private String path;

    private Integer isMenu;

//     为 了 方便 操作 ， 额外 添加两个字段  用来前端显示数据
    private Integer id;
    private String label ;


    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.permissionId;
    }
}

