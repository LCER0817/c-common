package com.ns.common.dao.spi.rabbitmq;

import com.ns.common.util.config.AbsNsMqConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.util.Objects;

/**
 * Created by liqiuwei on 2017/9/15.
 */
public abstract class AbsNsMqDao {

    private RabbitTemplate rabbitTemplate;

    public AbsNsMqDao(ConnectionFactory connectionFactory, AbsNsMqConfig config) {
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(config.getExchangeName());
        rabbitTemplate.setRoutingKey(config.getRoutingKey());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }


    protected <T> void convertAndSend(T obj) {
        if (Objects.isNull(obj)) {
            return;
        }
        rabbitTemplate.convertAndSend(obj);
    }

    protected <T> void convertAndSend(String routingKey, T obj) {
        if (StringUtils.isEmpty(routingKey) || Objects.isNull(obj)) {
            return;
        }
        rabbitTemplate.convertAndSend(routingKey, obj);
    }

    protected <T> void convertAndSend(T obj, MessagePostProcessor messagePostProcessor) {
        if (Objects.isNull(obj) || Objects.isNull(messagePostProcessor)) {
            return;
        }
        rabbitTemplate.convertAndSend(obj, messagePostProcessor);
    }

}
