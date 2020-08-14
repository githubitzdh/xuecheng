package com.czxy.xuecheng.manage_course.controller;

import com.czxy.xuecheng.api.course.CourseControllerApi;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.domain.course.Teachplan;
import com.czxy.xuecheng.domain.course.ext.CourseInfo;
import com.czxy.xuecheng.domain.course.ext.CourseView;
import com.czxy.xuecheng.domain.course.ext.TeachplanNode;
import com.czxy.xuecheng.domain.course.request.CourseListRequest;
import com.czxy.xuecheng.domain.course.response.CoursePicResult;
import com.czxy.xuecheng.domain.course.response.CoursePublishResult;
import com.czxy.xuecheng.manage_course.service.CourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/22 0022
 **/
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {
    @Resource
    private CourseService courseService;

    /**
     * 查询所有课程
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult<CourseInfo> findCourseList(@PathVariable("page") int page, @PathVariable("size") int size, CourseListRequest courseListRequest) {
        String company_id = "1";
        return courseService.findCourseList(company_id, size, page, courseListRequest);
    }


    /**
     * 查询指定课程的课程计划
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachplanList(courseId);
    }


    /**
     * 添加课程计划
     * @param teachplan
     * @return
     */
    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }

    /**
     * 添加课程图片,只允许添加一张图片,如果图片存在,将更新图片
     * @param courseId
     * @param pic
     * @return
     */
    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic")String pic) {
        return courseService.addCoursePic(courseId,pic);
    }


    /**
     * 查询课程图片
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/coursepic/list/{courseId}")
    public CoursePicResult findCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.findCoursePic(courseId);
    }

    /**
     * 删除课程图片
     * @return
     */
    @Override
    @DeleteMapping("/coursepic/del/{courseId}")
    public ResponseResult deleteCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.deleteCoursePic(courseId);
    }


    /**
     * 查询课程详情数据
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/courseview/{courseId}")
    public CourseView courseview(@PathVariable("courseId") String courseId) {
        return courseService.getCoruseView(courseId);
    }

    /**
     * 课程预览
     * @param courseId
     * @return
     */
    @Override
    @PostMapping("/preview/{courseId}")
    public CoursePublishResult preview(@PathVariable("courseId") String courseId) {
        return courseService.preview(courseId);
    }


    /**
     * 课程发布
     * @param courseId
     * @return
     */
    @Override
    @PostMapping("/publish/{courseId}")
    public CoursePublishResult publish(@PathVariable("courseId") String courseId) {
        return courseService.publish(courseId);
    }
}
