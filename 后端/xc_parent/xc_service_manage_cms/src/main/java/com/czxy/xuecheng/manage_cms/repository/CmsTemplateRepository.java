package com.czxy.xuecheng.manage_cms.repository;

import com.czxy.xuecheng.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/2 0002
 **/
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {

    /**
     * 根据模板名查询
     * @param templateName
     * @return
     */
    CmsTemplate findByTemplateName(String templateName);
}
