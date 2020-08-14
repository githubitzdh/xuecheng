package com.czxy.xuecheng;

import com.czxy.xuecheng.domain.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/15 0015
 **/
public class TestList {

    @Test
    public void test(){
        //模拟数据
        List<User> list = new ArrayList();
        list.add(new User("jack","1234",18));
        list.add(new User("rose","5678",21));

         //for循环
        for (User user : list) {
            System.out.println("第一种:"+user);
        }

        //iterator 迭代器
        Iterator<User> it = list.iterator();
       while (it.hasNext()){
           User next = it.next();
           System.out.println("第二种:"+next);
       }

       //普通循环
        for (int i = 0; i < list.size(); i++) {
            System.out.println("第三种:"+list.get(i));
        }


        //jdk8 forEach
         list.forEach(user->{
             System.out.println("第四种:"+user);
         });

        list.forEach(user-> System.out.println(user));

        list.forEach(System.out::println);



    }
}
