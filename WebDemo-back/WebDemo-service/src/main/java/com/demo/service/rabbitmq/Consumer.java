package com.demo.service.rabbitmq;

import cn.hutool.log.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 */
//@Component
public class Consumer {
    private static final Log LOG = Log.get();
    
    @RabbitListener(queues = RabbitMQConfig.TOPIC_QUEUE1)
    public void receiverTopic1(String message) {
        LOG.info("receiver topic queue1 message:"+message);
    }
    
    @RabbitListener(queues = RabbitMQConfig.TOPIC_QUEUE2)
    public void receiverTopic2(String message) {
        LOG.info("receiver topic queue2 message:"+message);
    }
}
