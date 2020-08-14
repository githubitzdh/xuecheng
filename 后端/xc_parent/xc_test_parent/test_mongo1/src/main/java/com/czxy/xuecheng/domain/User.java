package com.czxy.xuecheng.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/15 0015
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
private String username;
private String  password;
private  Integer age;
}
