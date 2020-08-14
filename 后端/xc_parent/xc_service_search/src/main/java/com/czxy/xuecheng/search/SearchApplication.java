package com.czxy.xuecheng.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/8/3 0003
 **/
@SpringBootApplication
@EnableEurekaClient
@EntityScan("com.czxy.xuecheng.domain.search")              //扫描实体类
@ComponentScan(basePackages = "com.czxy.xuecheng.common")      //通用接口
@ComponentScan(basePackages = "com.czxy.xuecheng.api")      //扫描接口
@ComponentScan(basePackages = "com.czxy.xuecheng.utils")        //工具
@ComponentScan(basePackages = "com.czxy.xuecheng.search")  //扫描本项目下的所有类
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
