package com.czxy.xuecheng.domain.course.ext;

import com.czxy.xuecheng.domain.course.CourseBase;
import com.czxy.xuecheng.domain.course.CourseMarket;
import com.czxy.xuecheng.domain.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *课程相关所有数据封装对象
 */
@Data
@NoArgsConstructor
@ToString
public class CourseView implements java.io.Serializable {
    private CourseBase courseBase;  // 基础信息
    private CoursePic coursePic;   // 课程图片
    private CourseMarket courseMarket; // 课程营销
    private TeachplanNode teachplanNode; // 教学计划
}
