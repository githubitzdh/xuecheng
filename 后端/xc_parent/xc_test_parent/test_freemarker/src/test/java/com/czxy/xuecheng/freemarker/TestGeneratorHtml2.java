package com.czxy.xuecheng.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/24 0024
 **/
public class TestGeneratorHtml2 {
    @Test
    public void tset1() throws Exception {
        //根据模板生成html
        // 1.核心配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2.设置模板文件夹
      // 2.1.获得类路径 /test_freemarker/target/test-classes/
        String classpath = this.getClass().getResource("/").getPath();
        System.out.println(classpath);
       // 2.2.静态目录
        File templateDir = new File(classpath);  //在resource根目录下创,不用额外路径
       // File templateDir = new File(classpath,"/templates/");
         // 2.3.给配置类设置操作目录
       configuration.setDirectoryForTemplateLoading(templateDir);
        // 3.设置编码
        configuration.setDefaultEncoding("UTF-8");

        // 4.获得具体模板 test.ftl
        Template template = configuration.getTemplate("course.ftl");
        //5.准备数据
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        String url = "http://localhost:31200/course/courseview/4028e581617f945f01617f9dabc40000";
        ResponseEntity<Map> entity = restTemplate.getForEntity(url, Map.class);
        Map<String,Object> map = entity.getBody();

        // 6.静态haul生产,字符串内容
        String s = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(s);
      // 7.将 "字符串"写入文件汇总
        File file = new File(templateDir,"course.html");
        FileUtils.writeStringToFile(file,s);
    }


}
