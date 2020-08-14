package com.czxy.xuecheng.search.service;

import com.czxy.xuecheng.common.model.response.CommonCode;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.common.model.response.QueryResult;
import com.czxy.xuecheng.domain.search.Course;
import com.czxy.xuecheng.domain.search.CourseSearchParam;
import com.czxy.xuecheng.search.repository.CourseSearchRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/8/3 0003
 **/
@Service
public class CourseSearchService {
    @Resource
    private CourseSearchRepository courseSearchRepository;

    /**
     * 搜索
     *
     * @param page
     * @param size
     * @param courseSearchParam
     * @return
     */
    public QueryResponseResult<Course> list(int page, int size, CourseSearchParam courseSearchParam) {
        // 1.准备查询条件,查询构建器
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 2.es.查询封装
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 2.1.查询条件
        queryBuilder.withQuery(boolQueryBuilder);
        // 2.2.分页条件, 分页从0开始
        queryBuilder.withPageable(PageRequest.of(page - 1, size));

        // 3.查询,获得结果
        Page<Course> search = courseSearchRepository.search(queryBuilder.build());
        QueryResult<Course> queryResult = new QueryResult<>();
        queryResult.setTotal(search.getTotalElements());
        queryResult.setList(search.getContent());
        // 4.封装结果
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }
}
