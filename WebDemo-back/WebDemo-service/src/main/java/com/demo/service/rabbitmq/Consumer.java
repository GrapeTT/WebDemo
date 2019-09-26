/*
 * Copyright (c) 2015 www.jd.com All rights reserved.
 * 本软件源代码版权归京东智能集团所有,未经许可不得任意复制与传播.
 */
package com.demo.service.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 */
//@Component
public class Consumer {
    private static Logger LOGGER = LoggerFactory.getLogger(Provider.class);
    
    @RabbitListener(queues = RabbitMQConfig.TOPIC_QUEUE1)
    public void receiverTopic1(String message) {
        LOGGER.info("receiver topic queue1 message:"+message);
    }
    
    @RabbitListener(queues = RabbitMQConfig.TOPIC_QUEUE2)
    public void receiverTopic2(String message) {
        LOGGER.info("receiver topic queue2 message:"+message);
    }
}