package com.czxy.xuecheng.guava;

import com.google.common.collect.ImmutableMap;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/8 0008
 **/
public class TestMap {

    public static void main(String[] args) {
        // 1.构建对象不可变Map
        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<>();

        // 2.添加数据
       mapBuilder.put("k1","v1");
       mapBuilder.put("k2","v2");
       mapBuilder.put("k3","v3");

       // 3.转换普通Map (不可变)
        ImmutableMap<String, String> map = mapBuilder.build();

        // 4.获得值
        String value = map.get("k1");
        System.out.println(value);

        // 5.不可以添加
    }
}
