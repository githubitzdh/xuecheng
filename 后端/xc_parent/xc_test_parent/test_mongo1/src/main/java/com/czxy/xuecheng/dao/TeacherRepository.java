package com.czxy.xuecheng.dao;

import com.czxy.xuecheng.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 **/
public interface TeacherRepository extends MongoRepository<Teacher,String> {

    /**
     * 根据名字查询
     * @param username
     * @return
     */
    Teacher findByUsername(String username);
    //根据名称模糊查询
    List<Teacher> findByUsernameLike(String username);
    //根据名称和年龄查询
    List<Teacher> findByUsernameLikeAndAge(String username, int age);
    //根据名称和年龄，分页查询
    Page<Teacher> findByUsernameLikeAndAge(String username, int age, PageRequest pageRequest);


}
