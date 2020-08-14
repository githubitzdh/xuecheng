package com.czxy.xuecheng.api.cms;

import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.domain.cms.CmsPage;
import com.czxy.xuecheng.domain.cms.request.QueryPageRequest;
import com.czxy.xuecheng.domain.cms.response.CmsPageResult;
import com.czxy.xuecheng.domain.cms.response.CmsPostPageResult;
import io.swagger.annotations.*;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/29 0029
 **/
@Api(tags = "cms页面管理接口", description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {

    /**
     *    //页面查询
     * @param page 分页,当前页
     * @param size 分页,每页显示个数
     * @param queryPageRequest 查询条件封装对象
     * @return 查询结果封装对象(状态码,查询结果)
     */
    @ApiOperation("页面的分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页",required = true,paramType = "path",dataType = "int"),
            @ApiImplicitParam(name="size",value="每页显示个数",required = true,paramType = "path",dataType = "int")
    })
    public QueryResponseResult<CmsPage> findList(int page , int size , QueryPageRequest queryPageRequest);


    /**
     * 添加
     * @param cmsPage
     * @return
     */
    @ApiOperation("新增页面")
    public CmsPageResult add(CmsPage cmsPage);



    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    @ApiOperation("根据id查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "唯一标识",required=true,paramType="path",dataType="int")
    })
    public CmsPageResult findById(String id);


    /**
     * 修改页面
     * @param id
     * @param cmsPage
     * @return
     */
    @ApiOperation("修改页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "唯一标识",required=true,paramType="path",dataType="int")
    })
    public CmsPageResult update (String id,CmsPage cmsPage);



    @ApiOperation("根据id删除数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "唯一标识",required = true,paramType = "path",dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 24006,message = "页面不存在"),
            @ApiResponse(code = 10000,message = "操作成功")
    })
    public ResponseResult delete(String id);


    /**
     * 页面发布
     * @param pageId
     * @return
     */
    @ApiOperation("页面发布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageId", value = "唯一标识", required = true, paramType = "path", dataType = "string")
    })
    public ResponseResult post(String pageId);


    /**
     * 保存页面
     * @param cmsPage
     * @return
     */
    @ApiOperation("保存页面")
    public CmsPageResult save(CmsPage cmsPage);

    /**
     * 一键发布页面
     * @param cmsPage
     * @return
     */
    @ApiOperation("一键发布页面")
    public CmsPostPageResult postPageQuick(CmsPage cmsPage);

}
