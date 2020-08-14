package com.czxy;

import com.czxy.xuecheng.StuApplication;
import com.czxy.xuecheng.dao.StudentRepository;
import com.czxy.xuecheng.domain.Student;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/17 0017
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StuApplication.class)
public class Stest {
    @Resource
    private StudentRepository studentRepository;
@Test
    public void run1(){
    MongoClientURI mongoClientURI = new MongoClientURI("mongodb://root:root@localhost:27017/demo");
    MongoClient mongoClient = new MongoClient(mongoClientURI);
    MongoDatabase demo = mongoClient.getDatabase("demo");
   demo.createCollection("student");
}

@Test
    public void run2(){
    Student student = new Student();
    student.setName("a");
    student.setPassword("a");
    student.setDescription("6");
    student.setEdu("高中");

    Student student2 = new Student();
    student2.setName("aa");
    student2.setPassword("aa");
    student2.setDescription("66");
    student2.setEdu("高中1");


    Student student3 = new Student();
    student2.setName("b");
    student2.setPassword("b");
    student2.setDescription("7");
    student2.setEdu("大学");

    ArrayList<Student> list = new ArrayList<>();
    list.add(student);
    list.add(student2);
    list.add(student3);
    studentRepository.insert(list);

}

@Test
    public void run3(){
    List<Student> all = studentRepository.findAll();
    System.out.println(all);
}

@Test
    public void test(){
    //1.定义变量
    int sum=0;
    //2.循环
    for (int i = 1; i <101 ; i++) {
      //求和值
        sum+=i;
    }
    ArrayList<Object> objects = new ArrayList<>();

    //展示
    System.out.println(sum);
}
}
