package com.czxy.xuecheng.filesystem.dao;

import com.czxy.xuecheng.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/25 0025
 **/
public interface FileSystemRepository extends MongoRepository<FileSystem,String> {
}
