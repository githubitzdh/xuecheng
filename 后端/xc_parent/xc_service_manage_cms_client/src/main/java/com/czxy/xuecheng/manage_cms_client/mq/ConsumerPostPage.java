package com.czxy.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.czxy.xuecheng.manage_cms_client.service.PageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/20 0020
 **/
@Component
public class ConsumerPostPage {
    @Resource
    private PageService pageService;

    @RabbitListener(queues = "${xuecheng.mq.queue}")
    public void postPage(String message) {
        // 1.字符创 -> json
        Map<String, String> map = JSON.parseObject(message, Map.class);
        // 2.获得页面id
        String pageId = map.get("pageId");
        // 3.发布操作
        pageService.savePageToServerPath(pageId);
    }
}
