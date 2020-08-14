package com.czxy.xuecheng.dao;

import com.czxy.xuecheng.domain.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/17 0017
 **/
public interface StudentRepository extends MongoRepository<Student,String> {

}
