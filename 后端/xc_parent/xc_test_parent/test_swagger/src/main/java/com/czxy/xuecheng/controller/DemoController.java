package com.czxy.xuecheng.controller;

import com.czxy.xuecheng.domain.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
http://localhost:8089/swagger-ui.html#/
 **/
@RestController
@RequestMapping("/demo")
@Api(tags = "入口程序接口",description = "这个Swagger入门程序的接口，提供基本CRUD操作")
public class DemoController {

    @GetMapping("/findAll")
    @ApiOperation("查询所有")
    public List findAll() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User(1,"aaa","bbb"));
        list.add(new User(2,"ccc","ddd"));
        return list;
    }

    @GetMapping("/{id}")
    @ApiOperation("通过id查询详情")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "id",value = "唯一标识",required = true,paramType = "path",dataType = "int")
   })
    public String findById(@PathVariable Integer id){
        return "查询成功"+id;
    }


    @PostMapping
    @ApiOperation("添加操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code=400,message = "参数异常"),
            @ApiResponse(code = 500,message = "服务器端异常"),
            @ApiResponse(code = 20000,message = "自定义状态异常")
    })
    public User save(@RequestBody User user){
        return user;
    }

}
