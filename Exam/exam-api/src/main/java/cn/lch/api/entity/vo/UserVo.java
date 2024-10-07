package cn.lch.api.entity.vo;

import lombok.Data;

/**
 * @Author: LCH
 * @Description: VO包不涉及业务逻辑，只是用来封装数据的传输
 * @Date: Create in 17:16 2024/8/10
 * @Modified By:
 */

@Data
public class UserVo {

    private Integer id;

    private String name;

    private String tel;

    private String email;

    private String password;
    //     添加一个code 方便传输值
    private String code ;

    private String uuid ;

}
