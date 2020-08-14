package com.czxy.xuecheng;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/8 0008
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MgTestApplication.class)
public class TestGridFS {
    @Resource
    private GridFsTemplate gridFsTemplate;

    @Test
    public void testStore() throws FileNotFoundException {
        // 1.获得文件流
        // 1.1.文件
        File file = new File("E:\\file\\index.html");
       // 1.2.流
        FileInputStream is = new FileInputStream(file);

        // 2.存储 .store(内容,名称,类型)
        ObjectId id = gridFsTemplate.store(is, "测试页面名称", "");
        System.out.println(id);
    }

    /**
     * 查询
     */
    @Test
    public void  testFind(){
        // 条件查询
        // 1.条件
        Query query = Query.query(Criteria.where("_id").is("5f053f2959d4df55b02e3565"));

        // 2.查询
        GridFSFile gridFSFile = gridFsTemplate.find(query).first();
        // 3.处理结果
        System.out.println(gridFSFile.getFilename());
    }

    @Test
    public void  testFindAll() {
        // 查询所有
        GridFsResource[] resources = gridFsTemplate.getResources("*");
        for (GridFsResource gfr : resources) {
            System.out.println(gfr.getFilename());
        }
    }

    /**
     * 下载
     * @throws IOException
     */
    @Test
    public void testDownLoad() throws IOException {
        // 下载id为5f053f2959d4df55b02e3565
        // 1.获得MongoClient
        MongoClientURI clientURI = new MongoClientURI("mongodb://root:root@localhost:27017");
        MongoClient mongoClient = new MongoClient(clientURI);

        // 2.获得数据库
        MongoDatabase database = mongoClient.getDatabase("demo");
        // 3.创建模块操作对象
        GridFSBucket gridFSBucket = GridFSBuckets.create(database);

        // 4.下载
        // 4.1.确定下载资源
        // 1.条件
        Query query = Query.query(Criteria.where("_id").is("5f053f2959d4df55b02e3565"));
        // 2.查询
        GridFSFile gridFSFile = gridFsTemplate.find(query).first();

        // 4.2.具体下载操作
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        // 5.获得下载内容
        String content = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
        System.out.println(content);


    }
}
