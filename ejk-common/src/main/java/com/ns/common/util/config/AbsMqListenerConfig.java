package com.ns.common.util.config;

import com.ns.common.util.mq.ExchangeUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * Created by lenovo on 2017/9/19.
 */
public abstract class AbsMqListenerConfig extends AbsNsMqConfig implements RabbitListenerConfigurer {

    private String queueName;

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Queue queue() {
        return new Queue(queueName);
    }


    public Binding binding() {
        Binding binding = ExchangeUtil.ExchangEnum.valueOf(getExchangeType().toUpperCase()).bindingExchange(queue(), exchange(), getRoutingKey());
        return binding;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}
