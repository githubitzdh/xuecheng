package com.czxy.xuecheng.manage_cms.repository;

import com.czxy.xuecheng.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/2 0002
 **/
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    // 根据页面名称、站点Id、页面webpath查询
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);

 }
