package com.czxy.xuecheng.listener;

import com.czxy.xuecheng.config.RabbitConfig_fanout;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/18 0018
 **/
//@Component
public class Consumer0Fanout {
@RabbitListener(queues = RabbitConfig_fanout.QUEUE_INFORM_EMAIL)
    public void receiveEmail(String msg, Message message){
    System.out.println("receive message is :" +msg);
}
/*
    @RabbitListener(queues = RabbitConfig.QUEUE_INFORM_SMS)
    public void receiveSmS(String msg , Message message){
        System.out.println("receive message is:" + msg);
    }
    */
}
