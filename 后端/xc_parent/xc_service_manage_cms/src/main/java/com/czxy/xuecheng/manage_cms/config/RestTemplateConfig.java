package com.czxy.xuecheng.manage_cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/9 0009
 **/
@Configuration
public class RestTemplateConfig {
@Bean
    public RestTemplate restTemplate() {
    return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
}
}
