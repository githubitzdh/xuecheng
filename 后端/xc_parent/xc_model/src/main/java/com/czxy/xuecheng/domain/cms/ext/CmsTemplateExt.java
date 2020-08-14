package com.czxy.xuecheng.domain.cms.ext;

import com.czxy.xuecheng.domain.cms.CmsTemplate;
import lombok.Data;
import lombok.ToString;

/**
 * CmsTemplate 扩展类
 */
@Data
@ToString
public class CmsTemplateExt extends CmsTemplate {

    //模版内容
    private String templateValue;

}
