package com.probie.web;

import com.probie.config.RabbitMqConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * create by wangfei on 2020-04-18
 */
@RestController
@RequestMapping("/api/rabbit")
public class rabbitProviderController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/prodecuA")
    public void produceA() {
        String context = "hello: " + new Date();
        System.out.println("Sender : " + context);
        this.amqpTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE_NAME, RabbitMqConfig.ROUTING_KEY_A,context);
    }

    @GetMapping("/prodecuB")
    public void produceB() {
        String context = "hello: " + new Date();
        System.out.println("Sender : " + context);
        this.amqpTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE_NAME, RabbitMqConfig.ROUTING_KEY_B,context);
    }
}
