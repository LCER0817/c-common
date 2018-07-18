package com.ns.common.util.mq;

import org.springframework.amqp.core.*;

import java.util.Map;

/**
 * Created by lenovo on 2017/9/16.
 */
public interface ExchangeUtil {
    enum ExchangEnum {
        DIRECT {
            @Override
            public Exchange createExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> args) {
                return new DirectExchange(name, durable, autoDelete, args);
            }

            @Override
            public Binding bindingExchange(Queue queue, Exchange exchange, String routingKey) {
                return BindingBuilder.bind(queue).to((DirectExchange) exchange).with(routingKey);
            }
        }, FANOUT {
            @Override
            public Exchange createExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> args) {
                return new FanoutExchange(name, durable, autoDelete, args);
            }

            @Override
            public Binding bindingExchange(Queue queue, Exchange exchange, String routingKey) {
                return BindingBuilder.bind(queue).to((FanoutExchange) exchange);
            }
        }, TOPIC {
            @Override
            public Exchange createExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> args) {
                return new TopicExchange(name, durable, autoDelete, args);
            }

            @Override
            public Binding bindingExchange(Queue queue, Exchange exchange, String routingKey) {
                return BindingBuilder.bind(queue).to((TopicExchange) exchange).with(routingKey);
            }
        },
        DELAYED {
            @Override
            public Exchange createExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> args) {
                return new CustomExchange(name, "x-delayed-message", durable, autoDelete, args);
            }

            @Override
            public Binding bindingExchange(Queue queue, Exchange exchange, String routingKey) {
                return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
            }
        };

        public abstract Exchange createExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> args);

        public abstract Binding bindingExchange(Queue queue, Exchange exchange, String routingKey);
    }
}
