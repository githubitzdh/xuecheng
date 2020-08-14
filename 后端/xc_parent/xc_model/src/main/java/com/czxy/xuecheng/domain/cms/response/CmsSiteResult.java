package com.czxy.xuecheng.domain.cms.response;

import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.common.model.response.ResultCode;
import com.czxy.xuecheng.domain.cms.CmsSite;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/20 0020
 **/
@Data
@NoArgsConstructor
public class CmsSiteResult extends ResponseResult {
    CmsSite cmsSite;
    public CmsSiteResult(ResultCode resultCode,  CmsSite cmsSite) {
        super(resultCode);
        this.cmsSite = cmsSite;
    }
}
