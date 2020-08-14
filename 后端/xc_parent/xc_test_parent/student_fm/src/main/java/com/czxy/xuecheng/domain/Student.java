package com.czxy.xuecheng.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/17 0017
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "student")
public class Student {
    @Id
    private String sid;
    private String name;
    private String password;
    private String edu;
    private String description;
}
