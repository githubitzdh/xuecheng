package com.czxy.xuecheng.manage_course.feign;

import com.czxy.xuecheng.domain.cms.CmsPage;
import com.czxy.xuecheng.domain.cms.response.CmsPageResult;
import com.czxy.xuecheng.domain.cms.response.CmsPostPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/29 0029
 **/
@FeignClient(value = "xc-service-manage-cms", path = "/cms/page")
public interface CmsPageFeign {

    /**
     * 保存页面
     * @param cmsPage
     * @return
     */
    @PostMapping("/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage);

    /**
     * 课程发布
     * @param cmsPage
     * @return
     */
    @PostMapping("/postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage);

}
