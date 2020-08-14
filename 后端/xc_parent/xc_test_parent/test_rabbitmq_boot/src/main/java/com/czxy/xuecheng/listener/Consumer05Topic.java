package com.czxy.xuecheng.listener;

import com.czxy.xuecheng.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/18 0018
 **/
@Component
public class Consumer05Topic {
    @RabbitListener(queues = RabbitConfig.QUEUE_INFORM_EMAIL)
    public void receiveEmail(String msg, Message message) {
        System.out.println("receive email message is :" + msg);
    }

  /*  @RabbitListener(queues = RabbitConfig.QUEUE_INFORM_SMS)
    public void receiveSmS(String msg , Message message){
        System.out.println("receive sms message is:" + msg);
    }*/
}
