package com.czxy.xuecheng.demo04_topic;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/17 0017
 **/
public class Consumer04 {
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email_topic";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms_topic";
    public static void main(String[] args) {
        try {
            // 1.连接
            // 1.1.连接工厂,准备基本信息
            ConnectionFactory connectionFactory = new ConnectionFactory();

            connectionFactory.setHost("localhost");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");
            connectionFactory.setVirtualHost("/");
            // 1.2.获得连接
            Connection connection = connectionFactory.newConnection();

            // 1.3.获得管道
            Channel channel = connection.createChannel();

            // 2.声明
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS,true, false, false, null);

            // 3.消费
            // 3.1.消费方式
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println(new String(body, "UTF-8"));
                }
            };
            // 3.2.消费
            channel.basicConsume(QUEUE_INFORM_EMAIL, true,consumer);
            channel.basicConsume(QUEUE_INFORM_SMS, true,consumer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
