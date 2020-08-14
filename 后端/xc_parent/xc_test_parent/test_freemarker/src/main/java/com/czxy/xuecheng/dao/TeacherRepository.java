package com.czxy.xuecheng.dao;

import com.czxy.xuecheng.domain.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/22 0022
 **/
public interface TeacherRepository extends MongoRepository<Teacher,String> {

}
