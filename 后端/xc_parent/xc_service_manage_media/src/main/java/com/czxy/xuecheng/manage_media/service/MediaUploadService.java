package com.czxy.xuecheng.manage_media.service;

import com.czxy.xuecheng.common.exception.ExceptionCast;
import com.czxy.xuecheng.common.model.response.CommonCode;
import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.domain.media.MediaFile;
import com.czxy.xuecheng.domain.media.response.CheckChunkResult;
import com.czxy.xuecheng.domain.media.response.MediaCode;
import com.czxy.xuecheng.manage_media.dao.MediaFileRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/8/6 0006
 **/
@Service
public class MediaUploadService {
@Resource
    private MediaFileRepository mediaFileRepository;

    /**
     * 文件上传前的注册，检查文件是否存在
     * 根据文件md5得到文件路径，规则如下：
     * 一级目录：md5的第一个字符
     * 二级目录：md5的第二个字符
     * 三级目录：md5
     * 文件名：md5+文件扩展名
     * @param fileMd5 文件md5值
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt 文件扩展名
     * @return
     */
    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        //1  检查文件在磁盘上是否存在
        // 1.1 文件所属目录的路径
        String fileFolderPath = this.getFileFolderPath(fileMd5);

        String filePath =this.getFilePath(fileFolderPath,fileMd5,fileExt);

        //文件是否存在
        File file = new File(filePath);
        boolean exists = file.exists();

        //2 检查文件信息在mongodb中是否存在
        Optional<MediaFile> optional = mediaFileRepository.findById(fileMd5);
        if(exists && optional.isPresent()){
            //文件存在
            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_EXIST);
        }
        //3
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 得到文件的路径
     * @param fileFolderPath
     * @param fileMd5
     * @param fileExt
     * @return
     */
    private String getFilePath(String fileFolderPath, String fileMd5, String fileExt) {
        //1 检查父目录
        File dirFile = new File(fileFolderPath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        //2
        return fileFolderPath + "/" + fileMd5 + "." +fileExt;
    }

    @Value("${xc-service-manage-media.upload-location}")
    String upload_location;

    /**
     * 得到文件所属目录路径
     * @param fileMd5
     * @return
     */
    private String getFileFolderPath(String fileMd5) {
        return  upload_location + "/" + fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/";
    }


    /**
     * 检查分块文件是否存在
     * @param fileMd5 文件md5
     * @param chunk 块的下标
     * @param chunkSize 块的大小
     * @return
     */
    public CheckChunkResult checkchunk(String fileMd5, Integer chunk, Integer chunkSize) {
        // 1 得到分块文件的所在目录
        String chunkFileFolderPath = this.getChunkFileFolderPath(fileMd5);
        // 2 判断块文件
        File chunkFile = new File(chunkFileFolderPath + chunk);
        if(chunkFile.exists()){
            //块文件存在
            return new CheckChunkResult(CommonCode.SUCCESS,true);
        }else {
            //不存在
            return new CheckChunkResult(CommonCode.SUCCESS, false);
        }
    }

    //得到块文件所属目录路径
    private String getChunkFileFolderPath(String fileMd5) {
        String path = getFileFolderPath(fileMd5) + "/chunk/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        return path;
    }


    /**
     * 上传分块
     * @param file
     * @param fileMd5
     * @param chunk
     * @return
     */
    public ResponseResult uploadchunk(MultipartFile file, String fileMd5, Integer chunk) {
        try {
            //1 得到分块目录
            String chunkDir = this.getChunkFileFolderPath(fileMd5);
            //2 到分块文件路径
            String chunkFile = chunkDir + chunk;
            //3 保存文件
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(chunkFile));
            return new ResponseResult(CommonCode.SUCCESS);
        } catch (IOException e) {
            return new ResponseResult(CommonCode.FAIL);
        }
    }


    /**
     * 合并文件
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt
     * @return
     */
    public ResponseResult mergechunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        //1 合并所有分块
        // 1.1 得到分块文件的属目录
        String chunkFileFolderPath = this.getChunkFileFolderPath(fileMd5);
        File chunkFileFolder = new File(chunkFileFolderPath);

        // 1.2 分块文件列表
        File[] files = chunkFileFolder.listFiles();
        List<File> fileList = Arrays.asList(files);

        // 1.3 创建一个合并文件
        String fileFolderPath = this.getFileFolderPath(fileMd5);
        String filePath = this.getFilePath(fileFolderPath, fileMd5, fileExt);
        File mergeFile = new File(filePath);

        // 1.4 执行合并
        mergeFile = this.mergeFile(fileList, mergeFile);
        if(mergeFile == null){
            //合并文件失败
            ExceptionCast.cast(MediaCode.MERGE_FILE_FAIL);
        }

        //2 校验文件的md5值
        boolean checkFileMd5 = this.checkFileMd5(mergeFile, fileMd5);
        if(!checkFileMd5){
            //校验文件失败
            ExceptionCast.cast(MediaCode.MERGE_FILE_CHECKFAIL);
        }

        //3 将文件的信息写入mongodb
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileId(fileMd5);
        mediaFile.setFileOriginalName(fileName);
        mediaFile.setFileName(fileMd5 + "." +fileExt);
        //文件路径保存相对路径
        String filePath1 = fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/";
        mediaFile.setFilePath(filePath1);
        mediaFile.setFileSize(fileSize);
        mediaFile.setUploadTime(new Date());
        mediaFile.setMimeType(mimetype);
        mediaFile.setFileType(fileExt);
        //状态为上传成功
        mediaFile.setFileStatus("301002");
        mediaFileRepository.save(mediaFile);
        //TODO 4 向MQ发送视频处理消息
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     *  校验文件的md5值
     * @param mergeFile
     * @param fileMd5
     * @return
     */
    private boolean checkFileMd5(File mergeFile, String fileMd5) {
        try {
            //创建文件输入流
            FileInputStream inputStream = new FileInputStream(mergeFile);
            //得到文件的md5
            String md5Hex = DigestUtils.md5Hex(inputStream);

            //和传入的md5比较
            if(fileMd5.equalsIgnoreCase(md5Hex)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private File mergeFile(List<File> chunkFileList, File mergeFile) {
        try {
            //如果合并文件已存在则删除，否则创建新文件
            if (mergeFile.exists()) {
                mergeFile.delete();
            } else {
                //创建一个新文件
                mergeFile.createNewFile();
            }

            //对块文件进行排序
            Collections.sort(chunkFileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    if(Integer.parseInt(o1.getName())>Integer.parseInt(o2.getName())){
                        return 1;
                    }
                    return -1;
                }
            });

            RandomAccessFile raf_write = new RandomAccessFile(mergeFile,"rw");
            byte[] b = new byte[1024];
            for(File chunkFile:chunkFileList){
                RandomAccessFile raf_read = new RandomAccessFile(chunkFile,"r");
                int len = -1;
                while ((len = raf_read.read(b))!=-1){
                    raf_write.write(b,0,len);
                }
                raf_read.close();
            }
            raf_write.close();
            return mergeFile;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
