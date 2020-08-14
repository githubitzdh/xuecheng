package com.czxy.xuecheng.api.cms;

import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.domain.cms.CmsTemplate;
import com.czxy.xuecheng.domain.cms.request.QueryTemplateRequest;
import com.czxy.xuecheng.domain.cms.response.CmsTemplateResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/6 0006
 **/
@Api(tags = "cms模板接口",description = "对模板进行增删改查操作")
public interface CmsTemplateControllerApi {

    @ApiOperation("查询所有模板")
    public QueryResponseResult<CmsTemplate> findAll();

    @ApiOperation("模板的分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页",required = true,paramType = "path",dataType = "int"),
            @ApiImplicitParam(name="size",value="每页显示个数",required = true,paramType = "path",dataType = "int")
    })
    public QueryResponseResult<CmsTemplate> pageTemplate(int page , int size , QueryTemplateRequest queryTemplateRequest);



    @ApiOperation("添加模板")
    public CmsTemplateResult addTemplate(CmsTemplate cmsTemplate);

    @ApiOperation("查询模板详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "唯一标识", required = true, paramType = "path", dataType = "int")
    })
    public CmsTemplateResult findByTemplateId(String id);

    @ApiOperation("修改模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "唯一标识", required = true, paramType = "path", dataType = "int")
    })
    public CmsTemplateResult  updateTemplate(String id, CmsTemplate cmsTemplate);


    @ApiOperation("删除模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "唯一标识", required = true, paramType = "path", dataType = "int")
    })
    public CmsTemplateResult delTemplate(String id);


    @ApiOperation("模板文件上传")
    public CmsTemplateResult upload(MultipartFile file);
}
