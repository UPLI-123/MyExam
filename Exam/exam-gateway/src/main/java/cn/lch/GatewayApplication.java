package cn.lch;

import cn.lch.api.client.UserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: LCH
 * @Description: 网关启动
 * @Date: Create in 9:35 2024/10/6
 * @Modified By:
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // 确保不加载数据源
@EnableFeignClients(clients = UserClient.class)
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args) ;
    }
}
