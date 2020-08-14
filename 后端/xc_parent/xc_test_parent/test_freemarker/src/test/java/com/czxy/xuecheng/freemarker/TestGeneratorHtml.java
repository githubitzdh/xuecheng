package com.czxy.xuecheng.freemarker;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.util.HashMap;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/24 0024
 **/
public class TestGeneratorHtml {
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
       // File templateDir = new File(classpath);  //在resource根目录下创,不用额外路径
        File templateDir = new File(classpath,"/templates/");
         // 2.3.给配置类设置操作目录
       configuration.setDirectoryForTemplateLoading(templateDir);
        // 3.设置编码
        configuration.setDefaultEncoding("UTF-8");

        // 4.获得具体模板 test.ftl
        Template template = configuration.getTemplate("test.ftl");
        //5.准备数据
        HashMap<String, String> map = new HashMap<>();
        map.put("name","ce999");

        // 6.静态haul生产,字符串内容
        String s = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(s);
      // 7.将 "字符串"写入文件汇总
        File file = new File(templateDir,"test.html");
        FileUtils.writeStringToFile(file,s);
    }

    @Test
    public void test2() throws Exception {
        // 1.核心配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2.设置处理文件夹
         // 2.1.获得类路径
        String path = this.getClass().getResource("/").getPath();
        // 2.2.获得目录
        File dirfile = new File(path, "/templates/");
        // 2.3. 设置目录
         configuration.setDirectoryForTemplateLoading(dirfile);

        // 3.设置编码
          configuration.setDefaultEncoding("UTF-8");

        // 4..获得模板
        Template template = configuration.getTemplate("test.ftl");
        // 5.准备数据
        HashMap<String, Object> map = new HashMap<>();
       map.put("name","8888");
        // 6.将数据填充到模板,获得解析后内容
        String str = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

        // 7.将内容写入到指定的文件
        File file = new File(dirfile, "test2.html");
        FileUtils.writeStringToFile(file,str);
    }

    @Test
    public void testString() throws Exception {
        // 1.定义字符串模板-后期数据从数据库查询
       String stringTemplate="<!DOCTYPE html>\n" +
               "<html lang=\"en\">\n" +
               "<head>\n" +
               "    <meta charset=\"UTF-8\">\n" +
               "    <title>Title</title>\n" +
               "</head>\n" +
               "<body>\n" +
               "${name}\n" +
               "</body>\n" +
               "</html>";
        // 2.声明模板加载器
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("myTemplate",stringTemplate);
        // 3.核心配置类,需要使用模板加载器,最终将字符串加载成模板
        Configuration configuration = new Configuration(Configuration.getVersion());
       configuration.setTemplateLoader(templateLoader);
        // 4.获得指定命名模板
        Template myTemplate = configuration.getTemplate("myTemplate", "UTF-8");

        // 5.准备数据
        HashMap<String, String> map = new HashMap<>();
        map.put("name","字符串填充666");
        // 6.静态化
        String str = FreeMarkerTemplateUtils.processTemplateIntoString(myTemplate, map);
        // 7.IO 写入
        String path = this.getClass().getResource("/templates").getPath();
        File file = new File(path, "test1.html");
        FileUtils.writeStringToFile(file,str);
    }

}
