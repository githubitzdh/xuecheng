package com.czxy.xuecheng.manage_course.dao;

import com.czxy.xuecheng.domain.course.ext.CourseInfo;
import com.czxy.xuecheng.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/22 0022
 **/
@Mapper
public interface CourseMapper {

    /**
     * 查询所有课程
     * @param courseListRequest
     * @return
     */
    public List<CourseInfo> findCourseListPage(CourseListRequest courseListRequest);
}
