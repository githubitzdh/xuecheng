package com.czxy.xuecheng.controller;

import com.alibaba.fastjson.JSONObject;
import com.czxy.xuecheng.dao.TeacherRepository;
import com.czxy.xuecheng.domain.Teacher;
import com.czxy.xuecheng.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/16 0016
 **/
@Controller    //注意:不能使用@RestController,需要返回一个视图(找页面)
public class FreemarkerController {
    @Resource
    private TeacherRepository teacherRepository;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/findall")
    public String findAll(Model model) {
        List<Teacher> tlist = teacherRepository.findAll();
        model.addAttribute("tlist", tlist);
        tlist.forEach(System.out::println);
        return "findall";
    }

    /**
     * 方式1：使用ModelAndView
     *
     * @return
     */
    @GetMapping("/model_a")
    public ModelAndView model_a() {
        // 1.创建模型视图对象
        ModelAndView modelAndView = new ModelAndView();
        // 1.1设置模型数据
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "jack");
        map.put("age", "18");
        modelAndView.addObject("user", map);
        // 1.2设置页面
        modelAndView.setViewName("model_a");
        // 2.返回
        return modelAndView;
    }

    /**
     * 方式2; 使用Model
     *
     * @param model
     * @return
     */
    @GetMapping("/model_b")
    public String model_b(Model model) {
        // 1.设置模型数据
        Map<String, String> map = new HashMap<>();
        map.put("name", "jack");
        map.put("age", "99");
        model.addAttribute("user", map);

        // 2.返回视图(页面)
        return "/model_b";
    }

    /**
     * 方式3：使用自定义数据
     */
    @GetMapping("/model_c")
    public String model_c(Map map) {
        //设置数据
        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", "jack");
        userMap.put("age", "20");
        map.put("user", userMap);

        //设置页面
        return "/model_c";
    }

    /**
     * list遍历
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Model model) {
        //设置模型（数据）
        List<User> list = new ArrayList();
        list.add(new User("jack", "1234", 18));
        list.add(new User("rose", "5678", 21));
        model.addAttribute("allUser", list);

        //返回页面
        return "list";
    }


    /**
     * 遍历Map
     */
    @GetMapping("/map")
    public String map(Model model) {
        //设置模型（数据）
        Map<String, User> map = new HashMap<>();
        map.put("user1", new User("jack", "1234", 18));
        map.put("user2", new User("rose", "5678", 21));

        model.addAttribute("allUser", map);
        //设置视图（页面）
        return "map";
    }

    /**
     * if指令
     *
     * @param model
     * @return
     */
    @GetMapping("/if")
    public String _if(Model model) {
        //设置数据
        model.addAttribute("token", 1234);
        model.addAttribute("token2", "5678");
        return "if";
    }


    @GetMapping("/if2")
    public String _if2(Map<String, Object> map) {
        map.put("token", 1234);
        map.put("token2", "5678");
        return "if";

    }

    /**
     * 兼容配置
     *
     * @param model
     * @return
     */
    @GetMapping("/iftest2")
    public String test2(Model model) {
        //存放字符串类型
        model.addAttribute("token", "1234");
        return "iftest2";
    }


    @GetMapping("/method")
    public String method(Model model) {
        //集合
        ArrayList<String> list = new ArrayList<>();
        list.add("abc");
        list.add("1234");
        model.addAttribute("list", list);
       //时间
        model.addAttribute("birthday", new Date());
        // 整形
        model.addAttribute("num", 12345678);
        //JSON数据
       model.addAttribute("userJSON", JSONObject.toJSONString(new User("aaa", "bbb", 33)));

        //设置页面
        return "method";
    }

}
