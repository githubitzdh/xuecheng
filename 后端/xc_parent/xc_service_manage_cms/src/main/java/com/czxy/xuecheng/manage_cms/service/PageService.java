package com.czxy.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.czxy.xuecheng.common.exception.ExceptionCast;
import com.czxy.xuecheng.common.model.response.CommonCode;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.common.model.response.QueryResult;
import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.domain.cms.CmsConfig;
import com.czxy.xuecheng.domain.cms.CmsPage;
import com.czxy.xuecheng.domain.cms.CmsSite;
import com.czxy.xuecheng.domain.cms.CmsTemplate;
import com.czxy.xuecheng.domain.cms.request.QueryPageRequest;
import com.czxy.xuecheng.domain.cms.response.CmsCode;
import com.czxy.xuecheng.domain.cms.response.CmsPageResult;
import com.czxy.xuecheng.domain.cms.response.CmsPostPageResult;
import com.czxy.xuecheng.manage_cms.config.RabbitmqConfig;
import com.czxy.xuecheng.manage_cms.repository.CmsConfigRepository;
import com.czxy.xuecheng.manage_cms.repository.CmsPageRepository;
import com.czxy.xuecheng.manage_cms.repository.CmsSiteRepository;
import com.czxy.xuecheng.manage_cms.repository.CmsTemplateRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/2 0002
 **/
@Service
public class PageService {
    @Resource
    private CmsPageRepository cmsPageRepository;

    @Resource
    private CmsTemplateRepository cmsTemplateRepository;

    @Resource
    private CmsSiteRepository cmsSiteRepository;

    /**
     * 查询所有页面
     *
     * @param page             分页,当前页
     * @param size             分页,每页显示个数
     * @param queryPageRequest 查询条件封装对象
     *                         QueryResponseResult: 查询结果封装对象,需要2个参数
     *                         参数1;ResultCode 查询结果状态码封装对象
     *                         通过实现类CommonCode设置状态码
     *                         参数2; QueryResult 查询数据封装对象,用于分页两个数据(结果List,总条数total)
     * @return
     */
    public QueryResponseResult<CmsPage> findList(int page, int size, QueryPageRequest queryPageRequest) {
        // 对象非空处理
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        // 1.条件
        // 1.1. 条件查询 Example.of(数据,匹配器)
        // 1) 准备查询数据
        CmsPage cmsPage = new CmsPage();
        // 2) 准备匹配器
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        // 3)查询条件;站点查询,别名模糊查询查询,模板查询
        // 站点
        if (StringUtils.isNotBlank(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        // 别名
        if (StringUtils.isNotBlank(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        // 模板id查询
        if (StringUtils.isNotBlank(queryPageRequest.getTemplateId()))
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        // 模糊查询
        // 4)查询条件
        Example<CmsPage> example = Example.of(cmsPage, matcher);
        // 1.2.分页--page 从0开始
        page = page - 1;
        if (page < 0) {
            page = 0;
        }
        PageRequest pageRequest = PageRequest.of(page, size);

        // 2.查询
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageRequest);
        // 3.封装查询结果
        // 3.1 结果内存封装
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());
        // 3.2 状态码封装
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }


    /**
     * 查询所有模板
     *
     * @return
     */
    public QueryResponseResult<CmsTemplate> findAllTemplate() {
        // 1.查询
        List<CmsTemplate> list = cmsTemplateRepository.findAll();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 添加
     *
     * @param cmsPage
     * @return
     */
    public CmsPageResult saveCmsPage(CmsPage cmsPage) {
        // 1.校验
        // 1.1.非空校验
        if (cmsPage == null) {
            // 抛出异常，非法参数异常..指定异常信息的内容
            //throw new CustomException(CmsCode.CMS_PAGE_NOTEXISTS);
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        // 1.2.业务校验 pageName,sitedid,pathWebPath
        // 校验页面名称、站点id,页面webpath的唯一性
        // 根据页面名称、站点id,页面webpath去cms_page集合，如果查到说明此页面已经存在，如果查询不到再继续添加
        CmsPage findCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (findCmsPage != null) {
            // 页面已经存在
            // 抛出异常，异常内容就是页面已经存在
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }

        //调用dao新增页面
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }


    /**
     * 查询所有站点
     *
     * @return
     */
    public QueryResponseResult<CmsSite> findAllSite() {
        // 1.查询
        List<CmsSite> list = cmsSiteRepository.findAll();
        // 2.封装
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    public CmsPageResult findById(String id) {
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


    /**
     * 修改页面
     *
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult update(String id, CmsPage cmsPage) {

        //1.查询 根据id从数据库查询页面信息
        CmsPage one = findById(id).getCmsPage();
        if (one != null) {
            //2.准备更新数据
            //设置要修改的数据
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新参数
            one.setPageParameter(cmsPage.getPageParameter());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新类型（静态/动态）
            one.setPageType(cmsPage.getPageType());
            //更新模板id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新dataUrl
            one.setDataUrl(cmsPage.getDataUrl());
            // 3.提交修改
            cmsPageRepository.save(one);
            // 4.返回提示
            return new CmsPageResult(CommonCode.SUCCESS, one);
        }
        // 4.1修改失败
        return new CmsPageResult(CmsCode.CMS_PAGE_NOTEXISTS, null);

    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public ResponseResult delete(String id) {
        // 查询
        CmsPage one = findById(id).getCmsPage();
        if (one == null) {
            return new ResponseResult(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        // 2.删除
        cmsPageRepository.deleteById(id);
        // 3.提示
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Resource
    private CmsConfigRepository cmsConfigRepository;


    /**
     * 根据id获得模型
     *
     * @param id
     * @return
     */
    public CmsConfig getConfigById(String id) {
        // 根据id查询详情
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        // 处理结果
        if (optional.isPresent()) {
            CmsConfig cmsConfig = optional.get();
            //return new CmsConfigResult(CommonCode.SUCCESS, cmsConfig);
            return cmsConfig;
        }

        //return new CmsConfigResult(CmsCode.CMS_GENERATEHTML_DATAISNULL, null);
        return null;
    }


    /**
     * 获得页面静态化的内容
     *
     * @param pageId
     * @return
     */
    public String getPageHtml(String pageId) {
        // 1.通过id查询页面CmsPage
        CmsPage cmsPage = findById(pageId).getCmsPage();
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        // 2.通过dataUrl 获得当前页面需要的数据
        //CmsConfig cmsConfig = getModelByDataUrl(cmsPage.getDataUrl());
        Map map = getModelByDataUrl(cmsPage.getDataUrl());
        if (map == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        // 3.通过fileId获得模板
        String template = getTemplateById(cmsPage.getTemplateId());
        if (template == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        // 4.通过数据和模板,获得静态页面
        String html = generateHtml(template, map);
        if (html == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;
    }


    @Resource
    private RestTemplate restTemplate;

    /**
     * 根据远程调用获得数据
     *
     * @param dataUrl
     * @return
     */
    //private CmsConfig getModelByDataUrl(String dataUrl) {
    private Map getModelByDataUrl(String dataUrl) {
        // 1. 校验
        if (StringUtils.isBlank(dataUrl)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        // 2.远程调用
        //ResponseEntity<CmsConfigResult> entity = restTemplate.getForEntity(dataUrl, CmsConfigResult.class);
        ResponseEntity<Map> entity = restTemplate.getForEntity(dataUrl, Map.class);
        //return entity.getBody().getCmsConfig();
        return entity.getBody(); // 直接获得数据,没Result封装
    }

    @Resource
    private GridFSBucket gridFSBucket;
    @Resource
    private GridFsTemplate gridFsTemplate;

    /**
     * 通过模板id,获得模板文件内容
     *
     * @param templateId
     * @return
     */
    private String getTemplateById(String templateId) {
        try {
            // 1.通过id获得模板对象
            Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
            if (!optional.isPresent()) {
                ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
            }

            CmsTemplate cmsTemplate = optional.get();

            // 2.通过fileId,获得文件对象
            Query query = Query.query(Criteria.where("_id").is(cmsTemplate.getTemplateFileId()));
            GridFSFile gridFSFile = gridFsTemplate.findOne(query);
            // 3.获得下载流
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            // 4.资源的方式获得iO流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

            // 5.将IO流转字符串
            String content = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 根据文本格式模板+数据，进行静态化
     *
     * @param template
     * @param map
     * @return
     */
    public String generateHtml(String template, Map map) {
        try {
            // 1.生成配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            // 2.创建模板加载器,并设置 文本格式模板
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", template);
            // 3.将模板加载器设置配置类
            configuration.setTemplateLoader(stringTemplateLoader);
            // 4.获得FreeMarker 的模板对象
            Template template1 = configuration.getTemplate("template");
            // 5.通过工具类将模板对象转换html代码
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, map);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    /**
     * 页面发布
     *
     * @param pageId
     * @return
     */
    public ResponseResult postPage(String pageId) {
        //1 执行页面静态化
        String pageHtml = getPageHtml(pageId);

        //2 查询 CmsPage页面
        CmsPage cmsPage = findById(pageId).getCmsPage();
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }

        //3 将页面静态化文件存储到GridFs中,需要更新CmsPage --> fileId
        saveHtml(cmsPage, pageHtml);
        //4 将pageId消息发送到MQ
        sendPostPage(cmsPage);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 将页面静态化文件存储到GridFs中,需要更新CmsPage --> fileId
     *
     * @param cmsPage
     * @param pageHtml
     */
    private void saveHtml(CmsPage cmsPage, String pageHtml) {
        try {
            //1 删除静态页面
            String fileId = cmsPage.getHtmlFileId();
            if (StringUtils.isNotBlank(fileId)) {
                gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileId)));
            }

            //2 保存页面信息到 GridFS 中
            // 2.1.将字符串 转换 流
            InputStream inputStream = IOUtils.toInputStream(pageHtml, "UTF-8");
            // 2.2.保存
            ObjectId objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());

            //3 更新 CmsPage 的 htmlFileId自动, 记录GridFS存放信息
            cmsPage.setHtmlFileId(objectId.toString());
            cmsPageRepository.save(cmsPage);
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionCast.cast(CommonCode.FAIL);
        }
    }


    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 将pageId消息发送到MQ
     *
     * @param cmsPage
     */
    private void sendPostPage(CmsPage cmsPage) {
        //1 生成消息
        // 1.1 准备数据Map, 并转换json字符串
        Map<String, String> map = new HashMap<>();
        map.put("pageId", cmsPage.getPageId());
        // 1.2 转换json串
        String msg = JSON.toJSONString(map);

        //2.消息发送mq
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, cmsPage.getSiteId(), msg);
    }


    /**
     * 保存页面
     *
     * @param cmsPage
     * @return
     */
    public CmsPageResult savePage(CmsPage cmsPage) {
        // 1.查询
        CmsPage findCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        System.out.println(findCmsPage+ "2222");
        if (findCmsPage != null) {
            // 2.1.如果存在,则更新
            return this.update(findCmsPage.getPageId(), cmsPage);
        } else {
            // 2.2.如果不存在,添加
            return this.saveCmsPage(cmsPage);
        }
    }


    /**
     * 一键发布页面
     *
     * @param cmsPage
     * @return
     */
    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {
        // 1.保存页面
        CmsPageResult cmsPageResult = savePage(cmsPage);
        if (!cmsPageResult.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        // 2.进行页面发布
        // 2.1 得到页面的id
        CmsPage postCmsPage = cmsPageResult.getCmsPage();
        String pageId = postCmsPage.getPageId();

        // 2.2发布页面
        ResponseResult post = this.postPage(pageId);
        if (!post.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        // 3.拼接发布页面访问url路径
        // 拼接页面Url= cmsSite.siteDomain+ cmsPage.pageWebPath + cmsPage.pageName
        // 3.1.查询站点
        String siteId = postCmsPage.getSiteId();

        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        CmsSite cmsSite = optional.get();
        //CmsSite cmsSite = this.findCmsSiteById(siteId);  // 方式二
        //  3.2.拼凑页面url
        String pageUrl = cmsSite.getSiteDomain() + postCmsPage.getPageWebPath() + postCmsPage.getPageName();
        // 封装
        return new CmsPostPageResult(CommonCode.SUCCESS, pageUrl);
    }

    /**
     * 方式二:
     * 根据站点id查询站点信息
     */
    public CmsSite findCmsSiteById(String siteId) {
        // 查询
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
