package com.czxy.xuecheng.manage_course.dao;

import com.czxy.xuecheng.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/22 0022
 **/
@Mapper
public interface TeachplanMapper {
    //课程计划查询
    public TeachplanNode selectList(@Param("courseId") String courseId);
}
