package probie.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * create by wangfei on 2020-04-18
 */
@Configuration
@Component
public class RabbitMqConfig {
    public final static String QUEUE_NAME_A = "topic.message.a";
    public final static String QUEUE_NAME_B = "topic.message.b";

    public final static String ROUTING_KEY_A = "topic.message.key.a";
    public final static String ROUTING_KEY_B = "topic.message.key.b";

    public final static String TOPIC_EXCHANGE_NAME = "topic_exchange";
//    // 创建队列
//    @Bean
//    public Queue queueMessageA() {
//        return new Queue(RabbitMqConfig.QUEUE_NAME_A);
//    }
//    // 创建队列
//    @Bean
//    public Queue queueMessageB() {
//        return new Queue(RabbitMqConfig.QUEUE_NAME_B);
//    }
//    // 将对列绑定到Topic交换器
//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange(TOPIC_EXCHANGE_NAME, true, false);
//    }
//    // 将对列绑定到Topic交换器
//    @Bean
//    Binding bindingExchangeMessage(Queue queueMessageA, TopicExchange exchange) {
//        return BindingBuilder.bind(queueMessageA).to(exchange).with(ROUTING_KEY_A);
//    }
//    // 将对列绑定到Topic交换器 采用#的方式
//    @Bean
//    Binding bindingExchangeMessages(Queue queueMessageB, TopicExchange exchange) {
//        return BindingBuilder.bind(queueMessageB).to(exchange).with(ROUTING_KEY_B);
//    }

}
