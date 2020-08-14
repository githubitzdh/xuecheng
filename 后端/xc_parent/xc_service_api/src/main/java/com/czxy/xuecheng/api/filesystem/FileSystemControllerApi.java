package com.czxy.xuecheng.api.filesystem;

import com.czxy.xuecheng.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/25 0025
 **/
@Api(tags="文件系统接口",description = "维护文件系统中的各种文件")
public interface FileSystemControllerApi {

    /**
     * 上传文件
     * @param multipartFile 文件
     * @param filetag 文件标签
     * @param businesskey 业务key
     * @param metadata 元信息,json格式
     * @return
     */
    @ApiOperation("上传文件")
    public UploadFileResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata);

}
