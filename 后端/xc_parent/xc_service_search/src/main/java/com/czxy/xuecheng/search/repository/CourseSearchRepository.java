package com.czxy.xuecheng.search.repository;

import com.czxy.xuecheng.domain.search.Course;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/8/3 0003
 **/
public interface CourseSearchRepository extends ElasticsearchRepository<Course, String> {
}
