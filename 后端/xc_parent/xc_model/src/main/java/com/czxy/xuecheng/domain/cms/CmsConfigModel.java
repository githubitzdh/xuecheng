package com.czxy.xuecheng.domain.cms;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 *
 */
@Data
@ToString
public class CmsConfigModel {
    private String key;
    private String name;
    private String url;
    private Map mapValue; // 复杂值
    private String value; // 简答值

}
