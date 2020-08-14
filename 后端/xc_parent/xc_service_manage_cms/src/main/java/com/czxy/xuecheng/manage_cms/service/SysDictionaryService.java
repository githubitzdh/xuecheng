package com.czxy.xuecheng.manage_cms.service;

import com.czxy.xuecheng.common.model.response.CommonCode;
import com.czxy.xuecheng.domain.system.SysDictionary;
import com.czxy.xuecheng.domain.system.response.SysDictionaryResult;
import com.czxy.xuecheng.manage_cms.repository.SysDictionaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/22 0022
 **/
@Service
@Transactional
public class SysDictionaryService {
    @Resource
    private SysDictionaryRepository sysDictionaryRepository;

    /**
     * 通过类型查询对应数据字典
     * @param dType
     * @return
     */
    public SysDictionaryResult findByType(String dType){
        // 1.查询
        SysDictionary sysDictionary = sysDictionaryRepository.findByDType(dType);
        // 封装返回
        return new SysDictionaryResult(CommonCode.SUCCESS, sysDictionary );
    }
}
