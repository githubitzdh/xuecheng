package com.czxy.xuecheng.filesystem.controller;

import com.czxy.xuecheng.api.filesystem.FileSystemControllerApi;
import com.czxy.xuecheng.domain.filesystem.response.UploadFileResult;
import com.czxy.xuecheng.filesystem.service.FileSystemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 **/
@RestController
@RequestMapping("/filesystem")
public class FileSystemController  implements FileSystemControllerApi {
    @Resource
    private FileSystemService fileSystemService;

    /**
     * 上传图片
     * @param file
     * @param filetag 文件标签
     * @param businesskey 业务key
     * @param metadata 元信息,json格式
     * @return
     */
    @Override
    @PostMapping("/upload")
    public UploadFileResult upload(@RequestParam("file") MultipartFile file,
                                   @RequestParam(value = "filetag", required = true) String filetag,
                                   @RequestParam(value = "businesskey", required = false) String businesskey,
                                   @RequestParam(value = "metedata", required = false) String metadata) {
        return fileSystemService.upload(file,filetag,businesskey,metadata);
    }
}
