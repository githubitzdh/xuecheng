package com.czxy.xuecheng;

import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/27 0027
 **/
public class TestFastDFS02 {
@Test
    public void testFastDfs() {
    try {
        // 1.加载配置文件
        ClientGlobal.initByProperties("config/fastdfs-client.properties");
        // 2.获得Tracker客户端
        TrackerClient trackerClient = new TrackerClient();
        // 3.连接Tracker --> tracker服务
        TrackerServer trackerServer = trackerClient.getConnection();
        // 4.获得store存储服务
        StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
        // 5.获得存储客户端
        StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
        // 6.文件上传
        String filename = "E:\\file\\other\\img\\003.png";
        String fileId = storageClient1.upload_file1(filename, "png", null);
        System.out.println(fileId);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
