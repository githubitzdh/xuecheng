package com.czxy.xuecheng.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/17 0017
 **/
@Data
@NoArgsConstructor  //无参
@AllArgsConstructor // 有参
public class User {
private String username;
private String password;
private Integer age;
}
