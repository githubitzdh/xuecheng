package com.czxy.xuecheng.manage_cms.controller;

import com.czxy.xuecheng.api.system.SysDictionaryControllerApi;
import com.czxy.xuecheng.domain.system.response.SysDictionaryResult;
import com.czxy.xuecheng.manage_cms.service.SysDictionaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/22 0022
 **/
@RestController
@RequestMapping("/sys/dictionary")
public class SysDictionaryController implements SysDictionaryControllerApi {
    @Resource
    private SysDictionaryService sysDictionaryService;

    /**
     * 通过类型查询对应数据字典
     * @param dType
     * @return
     */
    @Override
    @GetMapping("/get/{dType}")
    public SysDictionaryResult findByType(@PathVariable("dType") String dType){
        return sysDictionaryService.findByType(dType);
    }

}
