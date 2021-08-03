package com.demo.service.rabbitmq;

import cn.hutool.log.Log;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生产者
 */
//@Component
public class Provider {
    private static final Log LOG = Log.get();
    
    @Autowired
    private AmqpTemplate amqpTemplate;
    
    public void sendTopic(String msg) {
        LOG.info("send topic message:" + msg);
        amqpTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, "topic.key1", msg + 1);
        amqpTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, "topic.key2", msg + 2);
    }
}
