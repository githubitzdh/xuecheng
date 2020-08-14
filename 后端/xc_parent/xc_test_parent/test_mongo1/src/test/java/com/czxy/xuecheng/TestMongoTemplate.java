package com.czxy.xuecheng;

import com.czxy.xuecheng.domain.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/22 0022
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MgTestApplication.class)
public class TestMongoTemplate {
    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 条件查询,等值查询
     */
    @Test
    public void testFind() {
        //等值查询
        //1.创建查询条件对象Query
        Query query = new Query();

        //2.设置等值查询条件
        query.addCriteria(Criteria.where("age").is(18));

        //3.查询
        List<Teacher> list = mongoTemplate.find(query, Teacher.class);
        //4.处理数据
        list.forEach(System.out::println);
    }

    /**
     * 条件查询
     */
    @Test
    public void testExample() {
        //gte 大于等于 ,gt大于, lte小于等于, lt小于
        //查询age小于于等于20
        Query query = new Query();
        query.addCriteria(Criteria.where("age").lt(20));
        List<Teacher> list = mongoTemplate.find(query, Teacher.class);
        list.forEach(System.out::println);

    }


    /**
     * 分页条件查询
     */
    @Test
    public void testPageExampleSearch() {
        //分页查询
        //1.分页查询
        int pageNum = 0;
        int pageSize = 2;
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);

        // 2.查询条件
        // 2.1.查询条件对象
        Query query = new Query();
        // 2.2.拼凑条件查询
        query.addCriteria(Criteria.where("age").is(18));

        //查询总条数
        long count = mongoTemplate.count(query, Teacher.class);
        System.out.println("总条数:" + count);

        // 2.3.添加分页条件
        query.with(pageRequest);

        // 3.查询
        List<Teacher> list = mongoTemplate.find(query, Teacher.class);
        // 4.处理结果
        list.forEach(System.out::println);


    }

    /**
     * 分页+排序
     */
    @Test
    public void testPageOrder() {
        // 分页+排序

        // 0排序
        Sort age = Sort.by(Sort.Order.desc("age"));

        //分页查询
        //1.分页查询
        int pageNum = 0;
        int pageSize = 5;
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize,age);

        // 2.查询条件
        // 2.1.查询条件对象
        Query query = new Query();
        // 2.2.拼凑条件查询
        query.addCriteria(Criteria.where("age").gte(18));

        //查询总条数
        long count = mongoTemplate.count(query, Teacher.class);
        System.out.println("总条数:" + count);

        // 2.3.添加分页条件
        query.with(pageRequest);

        // 3.查询
        List<Teacher> list = mongoTemplate.find(query, Teacher.class);
        // 4.处理结果
        list.forEach(System.out::println);


    }

    /**
     * 模糊查询
     */
    @Test
    public void testLike() {
        //模糊查询
        String username = "c";

        // 1.查询条件对象
        Query query = new Query();
        // 2.添加模糊查询-按照正则表达式进行匹配
        //   ^ 开始,  $结束
        //   . 匹配任意一个字符
        //   * 匹配个数大于等于0
        Pattern pattern = Pattern.compile("^.*" + username + ".*$", Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("username").regex(pattern));

        //3.查询
        List<Teacher> list = mongoTemplate.find(query, Teacher.class);
        //4.处理
        list.forEach(System.out::println);


    }


    @Test
    public void test1() {


    }


}
