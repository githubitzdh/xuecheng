package com.czxy.xuecheng.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/26 0026
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "user对象", description = "user对象描述信息")
public class User {
    @ApiModelProperty(value = "唯一标识", required = true)
    private Integer uid;
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = false)
    private String password;

}
