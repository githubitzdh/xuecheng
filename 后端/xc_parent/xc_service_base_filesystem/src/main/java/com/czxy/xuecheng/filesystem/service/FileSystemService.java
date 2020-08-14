package com.czxy.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.czxy.xuecheng.common.exception.ExceptionCast;
import com.czxy.xuecheng.common.model.response.CommonCode;
import com.czxy.xuecheng.domain.filesystem.FileSystem;
import com.czxy.xuecheng.domain.filesystem.response.FileSystemCode;
import com.czxy.xuecheng.domain.filesystem.response.UploadFileResult;
import com.czxy.xuecheng.filesystem.dao.FileSystemRepository;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 **/
@Service
public class FileSystemService {
    @Resource
    private FileSystemRepository fileSystemRepository;

    /**
     * 上传图片
     *
     * @param multipartFile
     * @param filetag
     * @param businesskey
     * @param metadata
     * @return
     */
    public UploadFileResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata) {
        //1. 将文件上传到fastDFS中，得到一个文件id
        String fileId = fdfs_upload(multipartFile);
        if (StringUtils.isBlank(fileId)) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_BUSINESSISNULL);
        }
        //2. 将文件id及其它文件信息存储到mongodb中
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setFileSize(multipartFile.getSize());
        fileSystem.setFileName(multipartFile.getOriginalFilename());
        fileSystem.setFileType(multipartFile.getContentType());
        fileSystem.setBusinesskey(businesskey);
        fileSystem.setFiletag(filetag);
        // metadata 提交的数据为json
        if (StringUtils.isNotBlank(metadata)) {
            Map map = JSON.parseObject(metadata, Map.class);
            fileSystem.setMetadata(map);
        }
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS, fileSystem);
    }

    /**
     * 将文件上传到 FastDFS中
     *
     * @param multipartFile 文件
     * @return 文件id
     */
    private String fdfs_upload(MultipartFile multipartFile) {
        //初始化fastDFS的环境
        initFdfsConfig();
        //1.创建trackerClient客户端
        TrackerClient trackerClient = new TrackerClient();
        try {
            // 2.连接tracker
            TrackerServer trackerServer = trackerClient.getConnection();
            // 3.获得storage存储服务器
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            // 4.创建storageClient来上传文件
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);

            // 5.上传文件
            //得到文件的原始名称
           /* String originalFilename = multipartFile.getOriginalFilename();
            //得到文件扩展名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String path = storageClient1.upload_file1(multipartFile.getBytes(), extName, null);
            return path;*/
            int index = multipartFile.getOriginalFilename().lastIndexOf(".") + 1;
            String extName = multipartFile.getOriginalFilename().substring(index);
            String path = storageClient1.upload_file1(multipartFile.getBytes(), extName, null);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Value("${xuecheng.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.charset}")
    String charset;

    /**
     * 初始化fastDFS环境
     */
    private void initFdfsConfig() {
        //初始化tracker服务地址（多个tracker中间以半角逗号分隔）
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
        } catch (Exception e) {
            e.printStackTrace();
            //抛出异常
            ExceptionCast.cast(FileSystemCode.FS_INITFDFSERROR);
        }
    }
}
