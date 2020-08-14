package com.czxy.xuecheng.api.course;

import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.domain.course.Teachplan;
import com.czxy.xuecheng.domain.course.ext.CourseInfo;
import com.czxy.xuecheng.domain.course.ext.CourseView;
import com.czxy.xuecheng.domain.course.ext.TeachplanNode;
import com.czxy.xuecheng.domain.course.request.CourseListRequest;
import com.czxy.xuecheng.domain.course.response.CoursePicResult;
import com.czxy.xuecheng.domain.course.response.CoursePublishResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/22 0022
 **/
@Api(tags = "课程管理接口", description = "课程的相关操作")
public interface CourseControllerApi {
    /**
     * 我的课程列表
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
     @ApiOperation("我的课程列表")
     @ApiImplicitParams({
             @ApiImplicitParam(name = "page", value = "当前页", required = true, paramType = "path", dataType = "int"),
             @ApiImplicitParam(name = "size", value = "每页显示个数", required = true, paramType = "path", dataType = "int")
     })
    public QueryResponseResult<CourseInfo>findCourseList(int page, int size, CourseListRequest courseListRequest);


    /**
     * 查询指定课程的课程计划
     * @param courseId
     * @return
     */
    @ApiOperation("查询指定课程的课程计划")
    @ApiImplicitParams({
            @ApiImplicitParam(name="courseId", value = "课程ID", required=true, paramType="path", dataType="string")
    })
    public TeachplanNode findTeachplanList(String courseId);


    /**
     * 添加课程计划
     * @param teachplan
     * @return
     */
    @ApiOperation("添加课程计划")
    public ResponseResult addTeachplan(Teachplan teachplan);


    /**
     * 添加课程图片
     * @param courseId
     * @param pic
     * @return
     */
    @ApiOperation("添加课程图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name="courseId", value = "课程ID", required=true, paramType="path", dataType="string"),
            @ApiImplicitParam(name="pic", value = "图片路径", required=true, paramType="path", dataType="string")
})
    public ResponseResult addCoursePic(String courseId,String pic);


    /**
     * 查询课程图片
     * @param courseId
     * @return
     */
    @ApiOperation("查询课程图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name="courseId", value = "课程ID", required=true, paramType="path", dataType="string")
    })
    public CoursePicResult findCoursePic(String courseId);


    /**
     * 删除课程图片
     * @param courseId
     * @return
     */
    @ApiOperation("删除课程图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name="courseId", value = "课程ID", required=true, paramType="path", dataType="string")
    })
    public ResponseResult deleteCoursePic(String courseId);


    /**
     * 查询课程详情数据
     * @param courseId
     * @return
     */
    @ApiOperation("查询课程详情数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="courseId",value = "课程ID",required=true,paramType="path",dataType="string")
    })
    public CourseView courseview(String courseId);


    /**
     * 预览课程
     * @param courseId
     * @return
     */
    @ApiOperation("预览课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true, paramType = "path", dataType = "string")
    })
    public CoursePublishResult preview(String courseId);

    /**
     * 课程发布
     * @param courseId
     * @return
     */
    @ApiOperation("课程发布")
    @ApiImplicitParams({
            @ApiImplicitParam(name="courseId",value = "课程ID",required=true,paramType="path",dataType="string")
    })
    public CoursePublishResult publish(String courseId);
}
