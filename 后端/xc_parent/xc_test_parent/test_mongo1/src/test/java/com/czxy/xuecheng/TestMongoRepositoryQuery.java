package com.czxy.xuecheng;

import com.czxy.xuecheng.dao.TeacherRepository;
import com.czxy.xuecheng.domain.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/24 0024
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MgTestApplication.class)
public class TestMongoRepositoryQuery {
    @Resource
    private TeacherRepository teacherRepository;
    @Test
    public void testQuery1(){
        // 使用example查询,age=18
        // 1,设置查询数据(默认等值查询)
        Teacher teacher = new Teacher();
       teacher.setAge(18);
        // 2.创建Example条件对象
        Example<Teacher> example = Example.of(teacher);
        // 3.查询
        List<Teacher> list = teacherRepository.findAll(example);
        // 4.打印
        list.forEach(System.out::println);
    }

    /**
     * 模糊查询
     */
    @Test
    public void testQuery2(){
        //模糊查询-姓a
        // 1.查询数据
        Teacher teacher = new Teacher();
       teacher.setUsername("a");
        // 2.查询条件
        // 2.1.设置匹配器,用于确定哪个字段进行什么查询(例如:模糊查询)
        ExampleMatcher matcher=ExampleMatcher.matching().withMatcher("username",ExampleMatcher.GenericPropertyMatchers.contains());
        // 2.2.创建Example条件对象
        Example<Teacher> example = Example.of(teacher, matcher);
        // 3.查询
        List<Teacher> list = teacherRepository.findAll(example);
        // 4.处理结果
       list.forEach(System.out::println);
    }
}
