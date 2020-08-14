package com.czxy.xuecheng.manage_cms.controller;

import com.czxy.xuecheng.common.web.BaseController;
import com.czxy.xuecheng.manage_cms.service.PageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/9 0009
 **/
@Controller
@RequestMapping("/cms/preview")
public class CmsPagePreviewController extends BaseController {
   @Resource
    private PageService pageService;

    @GetMapping("/{pageId}")
    public void preview(@PathVariable("pageId")String pageId) {
        // 1.调用
        String pageHtml = pageService.getPageHtml(pageId);

           try {
               // 2.使用工具类,将内容输出到浏览器
               // 字节
               //response.getOutputStream();
               // 字节流
              response.setContentType("text/html;charset=UTF-8");
              response.getWriter().print(pageHtml);
           } catch (IOException e) {
               e.printStackTrace();
           }

    }
}
