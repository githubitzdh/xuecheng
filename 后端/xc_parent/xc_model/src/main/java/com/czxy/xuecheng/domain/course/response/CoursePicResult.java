package com.czxy.xuecheng.domain.course.response;

import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.common.model.response.ResultCode;
import com.czxy.xuecheng.domain.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/25 0025
 **/
@Data
@NoArgsConstructor
public class CoursePicResult extends ResponseResult {
    private CoursePic coursePic;
    public CoursePicResult(ResultCode resultCode, CoursePic coursePic) {
        super(resultCode);
        this.coursePic = coursePic;
    }
}
