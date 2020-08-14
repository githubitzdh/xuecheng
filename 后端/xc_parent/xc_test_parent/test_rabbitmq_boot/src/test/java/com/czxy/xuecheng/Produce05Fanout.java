package com.czxy.xuecheng;

import com.czxy.xuecheng.config.RabbitConfig_fanout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/18 0018
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRabbitMQBootApplication.class)
public class Produce05Fanout {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Test
    public void testSendEmail() {
        for(int i = 0 ; i < 5 ; i++) {
            String message = "email inform to user" + i;
            rabbitTemplate.convertAndSend(RabbitConfig_fanout.EXCHANGE_FANOUT_INFORM, "inform.email", message);
            System.out.println("Send Message is:'" + message + "'");
        }
    }

    @Test
    public void testSendSms() {
        for(int i = 0 ; i < 5 ; i++) {
            String message = "sms inform to user" + i;
            rabbitTemplate.convertAndSend(RabbitConfig_fanout.EXCHANGE_FANOUT_INFORM, "inform.sms", message);
            System.out.println("Send Message is: '" + message + "'");
        }
    }

     @Test
    public void testSendSmsAndEmail() {
        for(int i = 0; i<5 ; i++) {
            String message = "sms and email inform to user" + i;
            rabbitTemplate.convertAndSend(RabbitConfig_fanout.EXCHANGE_FANOUT_INFORM, "inform.sms.email", message);
            System.out.println("Send Message is:'" +message + "'");
        }
     }
}

