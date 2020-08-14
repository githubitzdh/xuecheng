package com.czxy.xuecheng.domain.course.ext;

import com.czxy.xuecheng.domain.course.Teachplan;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 *
 */
@Data
@ToString
public class TeachplanNode extends Teachplan {

    List<TeachplanNode> children;

    //媒资文件id
    String mediaId;
    //媒资文件原始名称
    String mediaFileoriginalname;


}
