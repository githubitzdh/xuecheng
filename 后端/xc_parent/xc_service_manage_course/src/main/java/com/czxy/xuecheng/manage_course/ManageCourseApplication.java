package com.czxy.xuecheng.manage_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/22 0022
 **/
@SpringBootApplication
@EnableEurekaClient  // 开启Eureka 的客户端
@EnableFeignClients // 开启Feign远程调用客户端
@EntityScan(basePackages = "com.czxy.xuecheng.domain.course") //扫描其他包下的实体类
@ComponentScan(basePackages = "com.czxy.xuecheng.api")  // 扫描api接口
@ComponentScan(basePackages = "com.czxy.xuecheng.common")  // 扫描common通用类
@ComponentScan(basePackages = "com.czxy.xuecheng.utils") // 扫描工具类
@ComponentScan(basePackages = "com.czxy.xuecheng.manage_course")
// 扫描当前项目包,防止覆盖默认扫描规则
public class ManageCourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCourseApplication.class, args);
    }
}
