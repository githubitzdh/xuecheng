package com.czxy.xuecheng.manage_cms_client.service;

import com.czxy.xuecheng.common.model.response.CommonCode;
import com.czxy.xuecheng.domain.cms.response.CmsSiteResult;
import com.czxy.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.czxy.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import com.czxy.xuecheng.domain.cms.CmsPage;
import com.czxy.xuecheng.domain.cms.CmsSite;
import com.czxy.xuecheng.domain.cms.response.CmsCode;
import com.czxy.xuecheng.domain.cms.response.CmsPageResult;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.FileUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/20 0020
 **/
@Service
public class PageService {
    @Resource
    private CmsPageRepository cmsPageRepository;
    @Resource
    private CmsSiteRepository cmsSiteRepository;


    public void savePageToServerPath(String pageId) {
        try {
            // 1.获得文件id
            // 1.1.获得页面 CmsPage
            CmsPage cmsPage = findPageById(pageId).getCmsPage();
            // 1.2获得文件id
            String fileId = cmsPage.getHtmlFileId();

            // 2.从GridFS获得文件流
            InputStream is = getFileById(fileId);
            // 3.拼凑保存物理路径 =站点物理路径+页面物理路径+页面名称
            // 3.1.查询站点,并获得物理路径
            CmsSite cmsSite = findSiteById(cmsPage.getSiteId()).getCmsSite();
            // 3.2.拼凑
            String path = cmsSite.getSitePhysicalPath() + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
            // 4.保存文件
            FileUtils.copyInputStreamToFile(is, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Resource
    private GridFSBucket gridFSBucket;
    @Resource
    private GridFsTemplate gridFsTemplate;
    /**
     * 从GridFS获得文件流
     *
     * @param fileId
     * @return
     */
    private InputStream getFileById(String fileId) {
        try {
            // 1.通过id查询文件
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
            // 2.下载文件
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            // 3.通过资源对象,获得流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            return  gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    public CmsPageResult findPageById(String id) {
        // 1.查询
        // Optional JDk8特性,用于统一空值校验,防止空指针异常
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            // 2.封装
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        return new CmsPageResult(CmsCode.CMS_PAGE_NOTEXISTS, null);
    }

    public CmsSiteResult findSiteById(String id) {
        // 1.查询
        // Optional JDk8特性,用于统一空值校验,防止空指针异常
        Optional<CmsSite> optional = cmsSiteRepository.findById(id);
        if (optional.isPresent()) {
            CmsSite cmsSite = optional.get();
            // 2.封装
            return new CmsSiteResult(CommonCode.SUCCESS, cmsSite);
        }
        return new CmsSiteResult(CmsCode.CMS_PAGE_NOTEXISTS, null);
    }
}
