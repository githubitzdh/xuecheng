package com.czxy.xuecheng.demo02_fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhandehuang@itcast.cn
- 一个队列，对应一个消费者
- 一个交换机，绑定多个队列
- 消息发送给交换机，绑定的多个队列，将全部获得数据
 **/
public class Producer02 {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_FANOUT_INFORM = "inform_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
        try {
            // 1.获得管道
            // 1.1.连接工程
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");
            connectionFactory.setVirtualHost("/");
            // 1.2. 获得连接
            connection = connectionFactory.newConnection();
            // 1.3.获得管道
            channel = connection.createChannel();
            // 2.声明
            // 2.1.声明交换机
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, ExchangeTypes.FANOUT);
            // 2.2.声明队列
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            // 2.3.绑定交换机和队列
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_FANOUT_INFORM, "");
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_FANOUT_INFORM, "");
            // 3.给交换机发消息
            for (int i = 0; i < 5; i++) {
                String msg = "煌sir" + i;
                channel.basicPublish(EXCHANGE_FANOUT_INFORM, "", null, msg.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }
}
