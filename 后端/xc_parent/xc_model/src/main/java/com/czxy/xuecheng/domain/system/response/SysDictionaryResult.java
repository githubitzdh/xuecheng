package com.czxy.xuecheng.domain.system.response;

import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.common.model.response.ResultCode;
import com.czxy.xuecheng.domain.system.SysDictionary;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/22 0022
 **/
@Data
@NoArgsConstructor
public class SysDictionaryResult extends ResponseResult {
    private SysDictionary sysDictionary;

    public SysDictionaryResult(ResultCode resultCode, SysDictionary sysDictionary) {
        super(resultCode);
        this.sysDictionary = sysDictionary;
    }
}
