package com.czxy.xuecheng.manage_cms.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/9 0009
 **/
@Configuration
public class MongoDBConfig {
@Value("${spring.data.mongodb.database}")
   private String db;


    @Bean
    public GridFSBucket gridFSBuckets(MongoClient mongoClient) {
        // 获得数据
        MongoDatabase database = mongoClient.getDatabase(db);
        // 操作模板
        return GridFSBuckets.create(database);

    }

}
