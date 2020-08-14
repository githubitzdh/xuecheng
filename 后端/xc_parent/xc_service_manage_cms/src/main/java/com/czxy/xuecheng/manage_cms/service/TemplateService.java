package com.czxy.xuecheng.manage_cms.service;

import com.czxy.xuecheng.common.exception.ExceptionCast;
import com.czxy.xuecheng.common.model.response.CommonCode;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.common.model.response.QueryResult;
import com.czxy.xuecheng.domain.cms.CmsTemplate;
import com.czxy.xuecheng.domain.cms.request.QueryTemplateRequest;
import com.czxy.xuecheng.domain.cms.response.CmsCode;
import com.czxy.xuecheng.domain.cms.response.CmsTemplateResult;
import com.czxy.xuecheng.manage_cms.repository.CmsTemplateRepository;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/7 0007
 **/
@Service
public class TemplateService {
    @Resource
    private CmsTemplateRepository cmsTemplateRepository;
    @Resource
    private GridFsTemplate gridFsTemplate;

    /**
     * 添加模板
     * @param cmsTemplate
     * @return
     */
    public CmsTemplateResult addTemplate(CmsTemplate cmsTemplate) {
        // 校验
        // 1.1.非空校验
        if (cmsTemplate == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        // 1.2.唯一校验
        CmsTemplate c = cmsTemplateRepository.findByTemplateName(cmsTemplate.getTemplateName());
        if (c != null) {
           ExceptionCast.cast(CmsCode.CMS_TEMPLATE_EXISTS);
        }
        // 2.添加
        cmsTemplate.setTemplateId(null);
        cmsTemplateRepository.save(cmsTemplate);
        // 3.封装
        return new CmsTemplateResult(CommonCode.SUCCESS, cmsTemplate);
    }

    /**
     * 根据id查询模板
     * @param id
     * @return
     */
    public CmsTemplateResult findByTemplateId(String id) {
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(id);
        if (optional.isPresent()) {
            CmsTemplate cmsTemplate = optional.get();
            return new CmsTemplateResult(CommonCode.SUCCESS, cmsTemplate);
        }
        return new CmsTemplateResult(CmsCode.CMS_PAGE_NOTEXISTS, null);
    }

    /**
     * 修改模板
     * @param id
     * @param cmsTemplate
     * @return
     */
    public CmsTemplateResult updateTemplate(String id, CmsTemplate cmsTemplate) {
        CmsTemplate one = findByTemplateId(id).getCmsTemplate();
        if (one != null) {
            one.setSiteId(cmsTemplate.getSiteId());
            one.setTemplateName(cmsTemplate.getTemplateName());
            one.setTemplateParameter(cmsTemplate.getTemplateParameter());
            //one.setTemplateFileId(cmsTemplate.getTemplateFileId());
            cmsTemplateRepository.save(one);
            return new CmsTemplateResult(CommonCode.SUCCESS, null);
        }
        return new CmsTemplateResult(CmsCode.CMS_PAGE_NOTEXISTS, null);
    }

    /**
     * 删除模板
     * @param id
     * @return
     */
    public CmsTemplateResult delTemplate(String id) {
        CmsTemplate one = findByTemplateId(id).getCmsTemplate();
        if (one == null) {
            return new CmsTemplateResult(CmsCode.CMS_PAGE_NOTEXISTS, null);
        }
        cmsTemplateRepository.deleteById(id);
        return new CmsTemplateResult(CommonCode.SUCCESS, null);
    }

    /**
     * 模板列表分页
     * @param page
     * @param size
     * @param queryTemplateRequest
     * @return
     */
    public QueryResponseResult<CmsTemplate> pageTemplate(int page, int size, QueryTemplateRequest queryTemplateRequest) {
        if (queryTemplateRequest == null) {
            queryTemplateRequest = new QueryTemplateRequest();
        }
        CmsTemplate cmsTemplate = new CmsTemplate();
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("templateName", ExampleMatcher.GenericPropertyMatchers.contains());

        if (StringUtils.isNotBlank(queryTemplateRequest.getSiteId())) {
            cmsTemplate.setSiteId(queryTemplateRequest.getSiteId());
        }
        if (StringUtils.isNotBlank(queryTemplateRequest.getTemplateName())) {
            cmsTemplate.setTemplateName(queryTemplateRequest.getTemplateName());
        }
        Example<CmsTemplate> example = Example.of(cmsTemplate, matcher);
        //分页--page 从0开始
        page = page - 1;
        if (page < 0) {
            page = 0;
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CmsTemplate> all = cmsTemplateRepository.findAll(example, pageRequest);
        // 3.封装查询结果
        // 3.1 结果内存封装
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());
        // 3.2 状态码封装
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }


    /**
     * 模板上传
     * @param file
     * @return
     */
    public CmsTemplateResult upload(MultipartFile file) {

        try {
            //1. 将文件上传到GridFS中
            // 向GridFS存储文件
            ObjectId objectId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), "");
            // 2.封装id
            CmsTemplate cmsTemplate = new CmsTemplate();
            cmsTemplate.setTemplateFileId(objectId.toString());
            return new CmsTemplateResult(CommonCode.SUCCESS, cmsTemplate);
        } catch (Exception e) {
            return new CmsTemplateResult(CommonCode.FAIL, null);
        }
    }

}
