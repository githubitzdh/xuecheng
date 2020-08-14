package com.czxy.xuecheng.api.cms;

import com.czxy.xuecheng.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/9 0009
 **/
@Api(tags = "cms配置管理接口",description = "cms配置管理接口,提供数据模型的管理,查询接口")
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询cms配置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="唯一标识",required = true,paramType = "path",dataType = "string")
    })
    //public CmsConfigResult getmodel(String id);
    public CmsConfig getmodel(String id);
}
