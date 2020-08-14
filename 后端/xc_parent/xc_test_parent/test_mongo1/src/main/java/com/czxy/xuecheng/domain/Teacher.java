package com.czxy.xuecheng.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/15 0015
 **/
@Data
@Document(collection = "teacher")
public class Teacher {
    @Id
    private String id;
    @Field("username")
    private String username;
    private String password;
    private Integer age;
}
