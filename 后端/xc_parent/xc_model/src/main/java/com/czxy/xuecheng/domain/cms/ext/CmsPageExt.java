package com.czxy.xuecheng.domain.cms.ext;

import com.czxy.xuecheng.domain.cms.CmsPage;
import lombok.Data;
import lombok.ToString;

/**
 * CmsPage 扩展类
 */
@Data
@ToString
public class CmsPageExt extends CmsPage {
    private String htmlValue;

}
