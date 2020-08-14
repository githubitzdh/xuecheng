package com.czxy.xuecheng.manage_cms.controller;

import com.czxy.xuecheng.api.cms.CmsPageControllerApi;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.domain.cms.CmsPage;
import com.czxy.xuecheng.domain.cms.request.QueryPageRequest;
import com.czxy.xuecheng.domain.cms.response.CmsPageResult;
import com.czxy.xuecheng.domain.cms.response.CmsPostPageResult;
import com.czxy.xuecheng.manage_cms.service.PageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * http://localhost:31001/swagger-ui.html
 **/
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {
  @Resource
  private PageService pageService;
    /**
     * @param page             分页,当前页
     * @param size             分页,每页显示个数
     * @param queryPageRequest 查询条件封装对象
     *                         QueryResponseResult: 查询结果封装对象,需要2个参数
     *                         参数1;ResultCode 查询结果状态码封装对象
     *                         通过实现类CommonCode设置状态码
     *                         参数2; QueryResult 查询数据封装对象,用于分页两个数据(结果List,总条数total)
     * @return
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<CmsPage> findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
/*        // 2.查询(模拟数据)
        ArrayList<CmsPage> list = new ArrayList<>();
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试页面");
        list.add(cmsPage);

        // 3.1.查询内容封装
        QueryResult queryResult = new QueryResult();
        // 查询结果
        queryResult.setList(list);
        // 总条数
        queryResult.setTotal(200);

        // 3.2.创建QueryResponseResult 结果封装类;状态码,查询内容
        QueryResponseResult responseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return responseResult;*/
       return pageService.findList(page,size,queryPageRequest);
    }

  /**
   * 添加
   * @param cmsPage
   * @return
   */
    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return pageService.saveCmsPage(cmsPage);
    }


  /**
   * 根据id查询详情
   * @param id
   * @return
   */
  @Override
  @GetMapping("/get/{id}")
  public CmsPageResult findById(@PathVariable("id") String id) {
    return pageService.findById(id);
  }

    /**
     * 修改
     * @param id
     * @param cmsPage
     * @return
     */
  @Override
  @PutMapping("/edit/{id}")
  public CmsPageResult update(@PathVariable("id")String id, @RequestBody CmsPage cmsPage) {
    return pageService.update(id,cmsPage);
  }

  /**
   * 删除
   * @param id
   * @return
   */
  @Override
  @DeleteMapping("/del/{id}")
  public ResponseResult delete(@PathVariable("id") String id) {
    return pageService.delete(id);
  }


  /**
   * 页面发布
   * @param pageId
   * @return
   */
  @Override
  @PostMapping("/postPage/{pageId}")
  public ResponseResult post(@PathVariable("pageId") String pageId) {
    return pageService.postPage(pageId);
  }

  /**
   * 保存页面
   * @param cmsPage
   * @return
   */
  @Override
  @PostMapping("/save")
  public CmsPageResult save(@RequestBody CmsPage cmsPage) {
    System.out.println(cmsPage+"11111");
    return pageService.savePage(cmsPage);
  }

  /**
   * 一键发布页面
   * @param cmsPage
   * @return
   */
  @Override
  @PostMapping("/postPageQuick")
  public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage) {
    return pageService.postPageQuick(cmsPage);
  }

}
