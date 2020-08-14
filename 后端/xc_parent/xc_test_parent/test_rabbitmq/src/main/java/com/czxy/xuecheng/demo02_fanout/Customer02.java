package com.czxy.xuecheng.demo02_fanout;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/17 0017
 **/
public class Customer02 {
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            // 1.获得连接工厂,配置基本信息
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");
            connectionFactory.setVirtualHost("/"); // 虚拟机
            // 2.获得连接
            connection = connectionFactory.newConnection();
            // 3.获得管道
            channel = connection.createChannel();

            // 4.声明队列
            // queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,Map<String, Object> arguments)
            // queueDeclare(队列名 , 是否持久 , 是否排他, 是否自动删除 , 参数)
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            // 5.消息消费
            // 5.1.消费方式
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("消费:" + new String(body, "UTF-8"));
                }
            };
            // 5.2.消费
            channel.basicConsume(QUEUE_INFORM_EMAIL, true, consumer);
            channel.basicConsume(QUEUE_INFORM_SMS, true, consumer);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

