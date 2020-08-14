package com.czxy.xuecheng.filesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/25 0025
 **/
@SpringBootApplication
@EntityScan("com.czxy.xuecheng.domain.filesystem")//扫描实体类
@ComponentScan(basePackages={"com.czxy.xuecheng.api"})//扫描接口
@ComponentScan(basePackages={"com.czxy.xuecheng.common"})//扫描通用类
@ComponentScan(basePackages={"com.czxy.xuecheng.filesystem"})//扫描本项目下的所有类
public class FileSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileSystemApplication.class, args);
    }
}
