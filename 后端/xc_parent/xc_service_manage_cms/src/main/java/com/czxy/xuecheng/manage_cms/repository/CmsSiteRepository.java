package com.czxy.xuecheng.manage_cms.repository;

import com.czxy.xuecheng.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/4 0004
 **/
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}
