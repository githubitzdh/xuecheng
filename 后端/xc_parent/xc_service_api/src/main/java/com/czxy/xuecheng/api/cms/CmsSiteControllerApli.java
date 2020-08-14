package com.czxy.xuecheng.api.cms;

import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.domain.cms.CmsSite;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/6 0006
 **/
@Api(tags = "cms站点接口",description = "对站点进行增删改查操作")
public interface CmsSiteControllerApli {
    @ApiOperation("查询所有站点")
    public QueryResponseResult<CmsSite>findAll();
}
