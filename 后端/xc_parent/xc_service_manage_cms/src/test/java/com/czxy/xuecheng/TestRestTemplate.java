package com.czxy.xuecheng;

import com.czxy.xuecheng.domain.cms.response.CmsConfigResult;
import com.czxy.xuecheng.manage_cms.ManageCmsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/11 0011
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManageCmsApplication.class)
public class TestRestTemplate {

    @Resource
    private RestTemplate restTemplate;

    @Test
    public void test() {
        String url = "http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f";
        ResponseEntity<CmsConfigResult> entity = restTemplate.getForEntity(url, CmsConfigResult.class);
        CmsConfigResult cmsConfigResult = entity.getBody();
        System.out.println(cmsConfigResult);

    }
}
