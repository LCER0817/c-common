package com.ns.common.util.feign;

import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Created by lenovo on 2017/9/6.
 */
public abstract class AbsFeignClient<T> {

    private Class<T> entityClass;

    private T instance;

    /**
     * 解码码器
     */
    private Encoder encoder;
    /**
     * 编码器
     */
    private Decoder decoder;
    /**
     * apiURL
     */
    private String apiURL;

    private Feign.Builder builder;

    public AbsFeignClient() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];

    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }


    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }

    @PostConstruct
    public void init() {
        builder = Feign.builder();
        builder.decoder(Objects.isNull(decoder) ? new GsonDecoder() : decoder);
        builder.encoder(Objects.isNull(encoder) ? new GsonEncoder() : encoder);
        instance = builder.target(entityClass, apiURL);
    }

    public T get() {
        return this.instance;
    }
}
