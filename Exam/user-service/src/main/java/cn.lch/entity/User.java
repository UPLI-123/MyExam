package cn.lch.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2022-04-09 19:33:26
 */
@SuppressWarnings("serial")
@Data
public class User extends Model<User> {
    
    private Integer id;
    
    private String name;
    
    private String tel;
    
    private String email;
    
    private String password;
//     添加一个code 方便传输值
    private String code ;
//   该用户当前的角色
    private String roleName;

//    记录当前角色的id
    private Integer roleId ;

    private String uuid ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    }

