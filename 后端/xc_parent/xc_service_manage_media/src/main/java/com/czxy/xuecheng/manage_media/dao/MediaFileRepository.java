package com.czxy.xuecheng.manage_media.dao;

import com.czxy.xuecheng.domain.media.MediaFile;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/8/6 0006
 **/
public interface MediaFileRepository extends MongoRepository<MediaFile,String> {

}
