package cn.lch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: LCH
 * @Description: 用户服务
 * @Date: Create in 9:35 2024/10/6
 * @Modified By:
 */
@SpringBootApplication
@MapperScan("cn.lch.mapper") // 将mapper包下进行扫描
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args) ;
    }
}
