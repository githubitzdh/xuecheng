package com.czxy.xuecheng.manage_cms.controller;

import com.czxy.xuecheng.api.cms.CmsTemplateControllerApi;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.domain.cms.CmsTemplate;
import com.czxy.xuecheng.domain.cms.request.QueryTemplateRequest;
import com.czxy.xuecheng.domain.cms.response.CmsTemplateResult;
import com.czxy.xuecheng.manage_cms.service.PageService;
import com.czxy.xuecheng.manage_cms.service.TemplateService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/2 0002
 **/
@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {
@Resource
    private PageService pageService;
@Resource
private TemplateService templateService;
    /**
     * 查询所有模板
     * @return
     */
    @GetMapping
    @Override
    public QueryResponseResult<CmsTemplate> findAll(){
        return pageService.findAllTemplate();
    }

    /**
     * 分页模板查询
     * @param page
     * @param size
     * @param queryTemplateRequest
     * @return
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<CmsTemplate> pageTemplate(@PathVariable("page") int page, @PathVariable("size") int size,  QueryTemplateRequest queryTemplateRequest) {

        return templateService.pageTemplate(page,size,queryTemplateRequest);
    }

    /**
     * 添加模板
     * @param cmsTemplate
     * @return
     */
    @Override
    @PostMapping("/add")
    public CmsTemplateResult addTemplate(@RequestBody CmsTemplate cmsTemplate) {
        return templateService.addTemplate(cmsTemplate);
    }

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    @Override
    @GetMapping("/get/{id}")
    public CmsTemplateResult findByTemplateId(@PathVariable("id") String id) {
        return templateService.findByTemplateId(id);
    }

    /**
     * 修改
     * @param id
     * @param cmsTemplate
     * @return
     */
    @Override
    @PutMapping("/edit/{id}")
    public CmsTemplateResult updateTemplate(@PathVariable("id") String id,@RequestBody CmsTemplate cmsTemplate) {
        System.out.println(id);
        System.out.println(cmsTemplate);
        return templateService.updateTemplate(id,cmsTemplate);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    @DeleteMapping("/del/{id}")
    public CmsTemplateResult delTemplate(@PathVariable("id") String id) {
        return templateService.delTemplate(id);
    }


    /**
     * 上传模板
     * @param file
     * @return
     */
    @Override
    @PostMapping("/upload")
    public CmsTemplateResult upload(MultipartFile file){
        return templateService.upload(file);
    }
}
