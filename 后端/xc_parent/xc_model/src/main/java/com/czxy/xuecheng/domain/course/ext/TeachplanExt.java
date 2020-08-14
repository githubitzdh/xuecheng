package com.czxy.xuecheng.domain.course.ext;

import com.czxy.xuecheng.domain.course.Teachplan;
import lombok.Data;
import lombok.ToString;

/**
 *
 */
@Data
@ToString
public class TeachplanExt extends Teachplan {

    //媒资文件id
    private String mediaId;

    //媒资文件原始名称
    private String mediaFileOriginalName;

    //媒资文件访问地址
    private String mediaUrl;
}
