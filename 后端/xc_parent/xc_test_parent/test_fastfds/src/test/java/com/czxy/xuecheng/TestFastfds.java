package com.czxy.xuecheng;

import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/25 0025
 **/
public class TestFastfds {
    @Test
    public void testUpload() {
        try {
            //加载fastdfs-client.properties配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //定义TrackerClient，用于请求TrackerServer
            TrackerClient trackerClient = new TrackerClient();
            //连接tracker
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取Stroage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建stroageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);
            //向stroage服务器上传文件
            //本地文件的路径
            String filePath = "E:\\file\\other\\img\\003.jpg";
            //上传成功后拿到文件Id
            String fileId = storageClient1.upload_file1(filePath, "png", null);
            System.out.println(fileId);
            //group1/M00/00/00/wKjIhV8eOviAZsQeAAOytRKzSHE474.png

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
