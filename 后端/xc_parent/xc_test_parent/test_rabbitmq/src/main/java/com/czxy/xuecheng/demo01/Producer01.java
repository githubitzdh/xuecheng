package com.czxy.xuecheng.demo01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/17 0017
 **/
public class Producer01 {
    //队列名称
    private static final String QUEUE = "helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = null;
        Connection connection = null;
        try {
            // 1.获得连接工厂,配置基本信息
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");
            connectionFactory.setVirtualHost("/"); // 虚拟主机
            // 2.获得连接
            connection = connectionFactory.newConnection();


            // 3.获得管道
            channel = connection.createChannel();
            // 4.声明队列
            //queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,Map<String, Object> arguments)
            //queueDeclare(队列名,  是否持久,  队列是否独占此连接, 是否自动删除, 参数)
            channel.queueDeclare(QUEUE, true, false, false, null);

            String message = "hellowordld小明" + System.currentTimeMillis();
            // 5.声明消息
            //basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
            //channel.basicPublish(交换机, 队列名称, 参数,消息内容);
            channel.basicPublish("", QUEUE, null, message.getBytes());
            System.out.println("Send Mesage is:'" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //必须管道先关闭,才能关闭连接,否则报错
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        }


    }
}
