package com.czxy.xuecheng.demo04_topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 - 一个队列，对应一个消费者
 - 一个交换机，绑定多个队列，绑定时需要确定routingKey，通知routingKey支持==通配符==
 - 消息发送给交换机，routingKey匹配的队列，将获得数据

 - 通配符：
 inform.#.sms.#.
 以下routingKey都可以匹配
 inform.sms				//两个#没有匹配任意内容
 inform.email.sms		//第一个#匹配 email，第二个#没有匹配内容
 inform.sms.email		//第一个#没有匹配内容，第二个#匹配 email
 inform.user.sms.email	//第一个#匹配user，第二个#匹配 email
 **/
public class Producer04 {
           // 队列名称
        private static final String QUEUE_INFORM_EMAIL = "queue_inform_email_topic";
        private static final String QUEUE_INFORM_SMS = "queue_inform_sms_topic";
        private static final String EXCHANGE_TOPIC_INFORM = "inform_exchange_topic";

        public static void main(String[] args) throws IOException, TimeoutException {
            Connection connection = null;
            // 1.3.获得管道
            Channel channel = null;
            try {
                //1.连接管道
                // 1.1.连接工厂
                ConnectionFactory connectionFactory = new ConnectionFactory();
                connectionFactory.setHost("localhost");
                connectionFactory.setPort(5672);
                connectionFactory.setUsername("guest");
                connectionFactory.setPassword("guest");
                connectionFactory.setVirtualHost("/");
                // 1.2获得连接
                connection = connectionFactory.newConnection();
                // 1.3.获得管道
                channel = connection.createChannel();

                // 2.声明
                // 2.1.声明交换机
                channel.exchangeDeclare(EXCHANGE_TOPIC_INFORM, ExchangeTypes.TOPIC);
                // 2.2.声明3个队列
                channel.queueDeclare(QUEUE_INFORM_EMAIL, true,false, false,null);
                channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
                //2.3.绑定
                channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_TOPIC_INFORM, "inform.#.email.#");
                channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_TOPIC_INFORM, "inform.#.sms.#");
                // 3.发布消息
                for(int i = 0 ; i <  5 ; i ++) {
                    String msg = "send email" + i;
                    channel.basicPublish(EXCHANGE_TOPIC_INFORM, "inform.email", null, msg.getBytes());
                }
                for(int i = 0 ; i < 5 ; i ++) {
                    String msg = "send sms" + i;
                    channel.basicPublish(EXCHANGE_TOPIC_INFORM, "inform.sms", null, msg.getBytes());
                }
                for (int i = 0 ; i < 5 ; i ++) {
                    String msg = "send sms and email" + i;
                    channel.basicPublish(EXCHANGE_TOPIC_INFORM, "inform.sms.email", null, msg.getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(channel != null) {
                    channel.close();
                }
                if(connection != null) {
                    connection.close();
                }
            }

    }
}
