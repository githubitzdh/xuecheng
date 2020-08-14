package com.czxy.xuecheng.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/18 0018
 **/
@Configuration
public class RabbitConfig {
    //队列名称
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email_topic";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms_topic";
    // 交换机名称
    public static final String EXCHANGE_TOPIC_INFORM = "inform_exchange_topic";

    /**
     * 交换机设置
     * ExchangeBuilder提供了fanout、direct、topic、header交换机类型的配置
     * channel.exchangeDeclare(EXCHANGE_TOPIC_INFORM, BuiltinExchangeType.TOPIC);
     * @return
     */
    @Bean(EXCHANGE_TOPIC_INFORM)
    public Exchange exchange_topic() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPIC_INFORM).durable(true).build();
    }

    /**
     *
     * 声明队列
     * channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);
     * channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);
     * @return
     */
    @Bean(QUEUE_INFORM_SMS)
    public Queue queue_inform_sms() {
        return new Queue(QUEUE_INFORM_SMS, true, false, false);
    }

    @Bean(QUEUE_INFORM_EMAIL)
    public Queue queue_inform_email() {
        return new Queue(QUEUE_INFORM_EMAIL, true, false, false);
    }

    /**
     * 交换机和队列的绑定
     * @param queue
     * @param exchange
     * @return
     *   channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_TOPIC_INFORM, " inform. # .email. # ");
     *   channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_TOPIC_INFORM, "inform.#.sms.#");
     */
      @Bean
    public Binding binding_queue_inform_sms(@Qualifier(QUEUE_INFORM_SMS) Queue queue, @Qualifier(EXCHANGE_TOPIC_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.sms.#").noargs();
      }

      @Bean
    public Binding binding_queue_inform_email(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue, @Qualifier(EXCHANGE_TOPIC_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.email.#").noargs();
      }
}
