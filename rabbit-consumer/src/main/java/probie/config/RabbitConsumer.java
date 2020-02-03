package probie.config;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * create by wangfei on 2020-04-18
 */
@Component
@RabbitListener(bindings  = @QueueBinding(value = @Queue(RabbitMqConfig.QUEUE_NAME_B),
        exchange = @Exchange(name = "test", type = ExchangeTypes.TOPIC)))
public class RabbitConsumer {

    @RabbitHandler
    public void receiver(String message){
        System.out.println("FanoutReceiver---fanout.a:"+message);
    }
}
