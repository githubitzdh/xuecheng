package com.czxy.xuecheng.manage_cms_client.dao;

import com.czxy.xuecheng.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/2 0002
 **/
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

 }
