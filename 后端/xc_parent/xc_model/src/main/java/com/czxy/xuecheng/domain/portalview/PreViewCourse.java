package com.czxy.xuecheng.domain.portalview;

import com.czxy.xuecheng.domain.course.CourseBase;
import com.czxy.xuecheng.domain.course.CourseMarket;
import com.czxy.xuecheng.domain.course.CoursePic;
import com.czxy.xuecheng.domain.course.ext.TeachplanNode;
import com.czxy.xuecheng.domain.report.ReportCourse;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 *
 */
@Data
@ToString
@Document(collection = "pre_view_course")
public class PreViewCourse implements Serializable{

    @Id
    private String id;
    private CourseBase courseBase;
    private CourseMarket courseMarket;
    private CoursePic coursePic;
    private TeachplanNode teachplan;
    //课程统计信息
    private ReportCourse reportCourse;

}
