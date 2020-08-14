package com.czxy.xuecheng.manage_cms.repository;

import com.czxy.xuecheng.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/22 0022
 **/
public interface SysDictionaryRepository extends MongoRepository<SysDictionary, String> {
    /**
     * 通过类型查询
     * @param dType
     * @return
     */
    public SysDictionary findByDType(String dType);
}
