package com.czxy.xuecheng.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/29 0029
 **/
@SpringBootApplication
@EnableEurekaClient               //Eureka的客户端
@EntityScan(basePackages = "com.czxy.xuecheng.domain.cms") //扫描其他包下的实体类
@ComponentScan(basePackages = "com.czxy.xuecheng.api")  // 扫描api接口
@ComponentScan(basePackages = "com.czxy.xuecheng.common")  // 扫描common通用类
@ComponentScan(basePackages = "com.czxy.xuecheng.utils") // 扫描工具类
@ComponentScan(basePackages = "com.czxy.xuecheng.manage_cms")  // 扫描当前项目包,防止覆盖默认扫描规则
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class,args);
    }
}
