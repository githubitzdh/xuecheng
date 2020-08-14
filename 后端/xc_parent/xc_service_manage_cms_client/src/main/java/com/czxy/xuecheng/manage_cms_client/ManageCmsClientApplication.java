package com.czxy.xuecheng.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/20 0020
 **/
@SpringBootApplication
@EntityScan(basePackages = "com.czxy.xuecheng.domain.cms") //扫描其他包下的实体类
@ComponentScan(basePackages = "com.czxy.xuecheng.utils") // 扫描工具类
@ComponentScan(basePackages = "com.czxy.xuecheng.manage_cms_client")  // 扫描当前项目包,防止覆盖默认扫描规则
public class ManageCmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsClientApplication.class,args);
    }
}
