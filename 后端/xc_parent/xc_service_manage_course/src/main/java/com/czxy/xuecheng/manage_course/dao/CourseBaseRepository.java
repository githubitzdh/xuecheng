package com.czxy.xuecheng.manage_course.dao;

import com.czxy.xuecheng.domain.course.CourseBase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/23 0023
 **/
public interface CourseBaseRepository  extends JpaRepository<CourseBase,String> {
}
