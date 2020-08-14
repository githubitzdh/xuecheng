package com.czxy.xuecheng.common.exception;

import com.czxy.xuecheng.common.model.response.ResultCode;

/** 异常工具类
 *  如果需要抛出异常，使用方式如下：
 *  ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
 * @version 1.0
 **/
public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
