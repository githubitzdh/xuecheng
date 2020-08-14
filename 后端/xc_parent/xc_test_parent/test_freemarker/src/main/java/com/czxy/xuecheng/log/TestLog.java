package com.czxy.xuecheng.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/3 0003
 **/
public class TestLog {

    //获得日志对象
    private static final Logger LOGGER= LoggerFactory.getLogger(TestLog.class);

    public void test(){
        //查询
        String username = null;
    //   2.根据情况时有不同的方法进行输出
        LOGGER.debug("在debug级别下,你看到的提示信息");
        LOGGER.info("在info级别下,你看到的提示信息");
        LOGGER.warn("在warn级别下,你看到的提示信息");
        LOGGER.error("在error级别下,你看到的提示信息");
    }
}
