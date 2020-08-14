package com.czxy.xuecheng.api.system;

import com.czxy.xuecheng.domain.system.response.SysDictionaryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/22 0022
 **/
@Api(tags = "数据字典接口", description = "数据字典接口，进行数据字典的查询")
public interface SysDictionaryControllerApi {


    /**
     * 通过类型查询对应数据字典
     * @param dType
     * @return
     */
    @ApiOperation("通过类型查询对应数据字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dType", value = "数据字典类型", required = true, paramType = "path", dataType = "string"),
    })
    public SysDictionaryResult findByType(String dType);





}

