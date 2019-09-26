/*
 * Copyright (c) 2015 www.jd.com All rights reserved.
 * 本软件源代码版权归京东智能集团所有,未经许可不得任意复制与传播.
 */
package com.demo.service.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生产者
 */
@Component
public class Provider {
    private static Logger LOGGER = LoggerFactory.getLogger(Provider.class);
    
    @Autowired
    private AmqpTemplate amqpTemplate;
    
    public void sendTopic(String msg) {
        LOGGER.info("send topic message:" + msg);
        amqpTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, "topic.key1", msg + 1);
        amqpTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, "topic.key2", msg + 2);
    }
}
