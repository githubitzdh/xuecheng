package com.czxy.xuecheng.api.search;

import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.domain.search.Course;
import com.czxy.xuecheng.domain.search.CourseSearchParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/8/3 0003
 **/
@Api(tags = "课程搜索接口", description = "完成课程的搜索服务")
public interface CourseSearchControllerApi {

    /**
     * 课程综合搜索
     * @param page
     * @param size
     * @param courseSearchParam 课程搜索参数
     * @return
     */
    @ApiOperation("课程综合1搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页",required = true,paramType = "path",dataType = "int"),
            @ApiImplicitParam(name="size",value="每页显示个数",required = true,paramType = "path",dataType = "int")
    })
    public QueryResponseResult<Course> list(int page, int size, CourseSearchParam courseSearchParam);


}
