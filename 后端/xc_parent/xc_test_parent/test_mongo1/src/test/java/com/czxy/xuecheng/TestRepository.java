package com.czxy.xuecheng;

import com.czxy.xuecheng.dao.TeacherRepository;
import com.czxy.xuecheng.domain.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/15 0015
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MgTestApplication.class)
public class TestRepository {
    @Resource
    private TeacherRepository teacherRepository;

    /**
     * 查询所有
     */
    @Test
    public void testfindall() {
        //查询所有
        List<Teacher> list = teacherRepository.findAll();
        for (Teacher teacher : list) {
            System.out.println(teacher);
        }
    }

    /**
     * 排序查询
     */
    @Test
    public void testorder() {
        //排序
        //List<Teacher> list = teacherRepository.findAll(Sort.by("password"));
        List<Teacher> list = teacherRepository.findAll(Sort.by(Sort.Order.desc("password")));

        for (Teacher teacher : list) {
            System.out.println(teacher);
        }
    }


    /**
     * 分页查询
     */
    @Test
    public void test2() {
        //分页
        int page = 1;  //从0开始
        int size = 2;
        Page<Teacher> teacherPage = teacherRepository.findAll(PageRequest.of(page, size));

        //处理分页数据
        //1.获得分页内容
        List<Teacher> list = teacherPage.getContent();
        //2.获得总记录数
        long total = teacherPage.getTotalElements();
        System.out.println("总条数:" + total);
        for (Teacher teacher : list) {
            System.out.println(teacher);
        }
    }

    /**
     * 分页jdk8 forEach
     */
    @Test
    public void test2_1() {
        //分页
        int page = 1;  //从0开始
        int size = 2;
        Page<Teacher> teacherPage = teacherRepository.findAll(PageRequest.of(page, size));

        //遍历数据
        //jdk8提供forEach(使用箭头函数进行遍历)
        teacherPage.forEach(teacher -> System.out.println(teacher));
        //打印简化版
        teacherPage.forEach(System.out::println);
    }


    /**
     * 添加
     */
    @Test
    public void testinsert() {
        Teacher teacher = new Teacher();
        teacher.setUsername("a");
        teacher.setAge(20);
        //插入数据
        teacherRepository.insert(teacher);
    }


    /**
     * 根据id查询
     */
    @Test
    public void test4() {
        Optional<Teacher> optional = teacherRepository.findById("5ee87e059f226749580a039f");
        //Optional 用于统一规范空指针异常处理
        if (optional.isPresent()) {  //teacher! =null
            Teacher teacher = optional.get();
            System.out.println(teacher);
        } else {
            System.out.println("没有数据");
        }

    }

    /**
     * 更新采用save方法，
     * - 如果数据存在，将发生更新操作
     * - 如果数据不存在，将发生添加操作
     */
    @Test
    public void testUpdate() {
        //更新操作
        //1.查询
        Optional<Teacher> optional = teacherRepository.findById("5ee87e059f226749580a039f");
        if (optional.isPresent()) {
            //2.修改数据
            Teacher teacher = optional.get();
            teacher.setUsername("哈哈");
            //3.更新
            teacherRepository.save(teacher);
        }

    }

    /**
     * 删除
     */
    @Test
    public void testdelete() {
        teacherRepository.deleteById("5ee87e059f226749580a039f");
    }


    /**
     * 自定义查询
     */
    @Test
    public void testSearch() {
        Teacher teacher = teacherRepository.findByUsername("aaa");
        System.out.println(teacher);

        //根据名称模糊查询
        List<Teacher> list = teacherRepository.findByUsernameLike("aaa");
      list.forEach(System.out::println);


        //根据名称和年龄查询
        List<Teacher> list3 = teacherRepository.findByUsernameLikeAndAge("aaa", 18);
        System.out.println(list3);

        //根据名称和年龄,分页查询
        int pageNum=0;
        int pageSize=2;
        Page<Teacher> page = teacherRepository.findByUsernameLikeAndAge("a", 18, PageRequest.of(pageNum, pageSize));
        System.out.println(page.getTotalElements());
     page.getContent().forEach(System.out::println);

    }





}
