package com.czxy.xuecheng.domain.cms.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/7 0007
 **/
@Data
@ApiModel("模板页面查询条件封装对象")
public class QueryTemplateRequest {
    @ApiModelProperty("站点id")
    private String siteId;
    @ApiModelProperty("模版ID")
    private String templateId;

    @ApiModelProperty("模版名称")
    private String templateName;

}
