package com.czxy.xuecheng.manage_cms.repository;

import com.czxy.xuecheng.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/9 0009
 **/
public interface CmsConfigRepository  extends MongoRepository<CmsConfig,String> {
}
