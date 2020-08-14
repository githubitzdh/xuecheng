package com.czxy.xuecheng.demo03_direct;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
注意：消费者需要实时监听队列，不能关闭连接。
 **/
public class Customer03 {
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_EMAIL2 = "queue_inform_email2";
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
            connectionFactory.setVirtualHost("/");

            //2.获得连接
            connection = connectionFactory.newConnection();
            // 3.获得管道
            channel = connection.createChannel();
            // 4.声明队列
            // queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,Map<String, Object> arguments)
            // queueDeclare(队列名 , 是否持久 , 是否排他, 是否自动删除 , 参数)
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_EMAIL2, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);

            // 5.消息消费
            // 5.1.消费方式
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                /**
                 * 消费者接收消息调用此方法
                 * @param consumerTag 消费者的标签，在channel.basicConsume()去指定
                 * @param envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志(收到消息失败后是否需要重新发送)
                 * @param properties
                 * @param body
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("消费:" + new String(body, "UTF-8"));
                }
            };
            // 5.2.消费
            /**
             * 监听队列String queue, boolean autoAck,Consumer callback
             * 参数明细
             * 1、队列名称
             * 2、是否自动回复，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置为false则需要手动回复
             * 3、消费消息的方法，消费者接收到消息后调用此方法
             */
            channel.basicConsume(QUEUE_INFORM_SMS, true, consumer);
            channel.basicConsume(QUEUE_INFORM_EMAIL, true, consumer);
            channel.basicConsume(QUEUE_INFORM_EMAIL2, true, consumer);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
