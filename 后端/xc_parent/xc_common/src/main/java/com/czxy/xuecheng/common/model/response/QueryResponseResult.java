package com.czxy.xuecheng.common.model.response;

import lombok.Data;
import lombok.ToString;

/**
 * 查询结果集封装对象
 * @param <T>
 */
@Data
@ToString
public class QueryResponseResult<T> extends ResponseResult {

    QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode,QueryResult queryResult){
        super(resultCode);
       this.queryResult = queryResult;
    }

}
