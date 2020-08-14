package com.czxy.xuecheng.domain.cms.response;

import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.common.model.response.ResultCode;
import com.czxy.xuecheng.domain.cms.CmsTemplate;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/7 0007
 **/
@Data
@NoArgsConstructor
public class CmsTemplateResult extends ResponseResult {
    CmsTemplate cmsTemplate;
    public CmsTemplateResult(ResultCode resultCode, CmsTemplate cmsTemplate) {
        super(resultCode);
        this.cmsTemplate = cmsTemplate;
    }
}
