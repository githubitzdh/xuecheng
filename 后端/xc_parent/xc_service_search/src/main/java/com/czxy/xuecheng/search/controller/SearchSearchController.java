package com.czxy.xuecheng.search.controller;

import com.czxy.xuecheng.api.search.CourseSearchControllerApi;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.domain.search.Course;
import com.czxy.xuecheng.domain.search.CourseSearchParam;
import com.czxy.xuecheng.search.service.CourseSearchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/8/3 0003
 **/
@RestController
@RequestMapping("/search/course")
public class SearchSearchController implements CourseSearchControllerApi {
  @Resource
  private CourseSearchService courseService;

  /**
   * 课程搜索
   * @param page
   * @param size
   * @param courseSearchParam 课程搜索参数
   * @return
   */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<Course> list(@PathVariable("page") int page, @PathVariable("size") int size, CourseSearchParam courseSearchParam) {
      return  courseService.list(page, size, courseSearchParam);
    }
}
