package com.ns.common.util.config;

import com.ns.common.util.mq.ExchangeUtil;
import org.springframework.amqp.core.Exchange;

import java.util.Map;

/**
 * Created by liqiuwei on 2017/9/15.
 */
public abstract class AbsNsMqConfig {

    private String exchangeName;

    private String exchangeType;

    private String routingKey;

    private Boolean durable = true;

    private Boolean autoDelete = false;

    private Map<String, Object> args;

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public void setDurable(Boolean durable) {
        this.durable = durable;
    }

    public void setAutoDelete(Boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public Exchange exchange() {
        return ExchangeUtil.ExchangEnum.valueOf(exchangeType.toUpperCase()).createExchange(exchangeName, durable, autoDelete, args);
    }

}
