package com.czxy.xuecheng.controller;

import com.czxy.xuecheng.dao.StudentRepository;
import com.czxy.xuecheng.domain.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/17 0017
 **/
@Controller
@RequestMapping("/student")
public class StController {
    @Resource
    private StudentRepository studentRepository;

    @GetMapping("/test")
    public String test() {
        return "/test";
    }

    /**
     * 查询所有
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String run1(Model model) {
        List<Student> all = studentRepository.findAll();
        model.addAttribute("slist", all);
        System.out.println(all);
        return "list";
    }


    /**
     * 添加
     *
     * @return
     */
    @PostMapping("/add")
    public String stadd(Student student) {
        //插入数据
        studentRepository.insert(student);
        return "redirect:/student/list";
    }

    @GetMapping("/findById")
    public String findById(Model model,String sid){
        Optional<Student> op = studentRepository.findById(sid);
       if(op.isPresent()){
           Student student = op.get();
           model.addAttribute("slist",student);
       }
        return "ed";
    }

    /**
     * 修改
     *
     * @return
     */
    @PostMapping("/ed")
    public String sted(Student student) {

        studentRepository.save(student);

        return "redirect:/student/list";
    }

    /**
     * 删除
     *
     * @return
     */
    @GetMapping("/del")
    public String stdel(String sid) {
        studentRepository.deleteById(sid);
        return "redirect:/student/list";
    }


}
