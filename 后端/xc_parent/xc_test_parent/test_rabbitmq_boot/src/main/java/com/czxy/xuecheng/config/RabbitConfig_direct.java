package com.czxy.xuecheng.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/18 0018
 **/
//@Configuration
public class RabbitConfig_direct {
    // 交换机名称
    public static final String EXCHANGE_DIRECT_INFORM = "inform_exchange_direct";

    //队列名称
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email_direct";
    public static final String QUEUE_INFORM_EMAIL2 = "queue_inform_email2";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms_direct";

    /**
     * 交换机设置
     * ExchangeBuilder提供了fanout、direct、topic、header交换机类型的配置
     * channel.exchangeDeclare(EXCHANGE_TOPIC_INFORM, BuiltinExchangeType.TOPIC);
     * @return
     */
    @Bean(EXCHANGE_DIRECT_INFORM)
    public Exchange exchange_direct() {
        return ExchangeBuilder.directExchange(EXCHANGE_DIRECT_INFORM).durable(true).build();
    }

    /**
     *
     * 声明队列
     * channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
     * @return
     */
    @Bean(QUEUE_INFORM_SMS)
    public Queue queue_inform_sms() {
        return new Queue(QUEUE_INFORM_SMS);
    }
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue queue_inform_email() {
        return new Queue(QUEUE_INFORM_EMAIL, true, false, false);
    }
    @Bean(QUEUE_INFORM_EMAIL2)
    public Queue queue_inform_email2() {
        return new Queue(QUEUE_INFORM_EMAIL, true, false, false);
    }

      @Bean
    public Binding binding_queue_inform_sms_direct(@Qualifier(QUEUE_INFORM_SMS) Queue queue, @Qualifier(EXCHANGE_DIRECT_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.sms.#").noargs();
      }
      @Bean
    public Binding binding_queue_inform_email_direct(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue, @Qualifier(EXCHANGE_DIRECT_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.email.#").noargs();
      }
}
