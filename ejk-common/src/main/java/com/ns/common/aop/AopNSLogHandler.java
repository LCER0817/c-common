package com.ns.common.aop;

import com.ns.common.util.gson.GsonUtil;
import com.ns.common.util.log.LoggerUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.springframework.cloud.netflix.feign.FeignClient;

public class AopNSLogHandler {

    private static final Logger opLogger = LoggerUtil.getOpLog();
    private static final Logger feignLogger = LoggerUtil.getFeignLog();

    public static Object handle(ProceedingJoinPoint joinPoint) {

        long start = System.currentTimeMillis();
        String interfaceName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        Object[] args = joinPoint.getArgs();
        Class<?>[] interfaces = joinPoint.getTarget().getClass().getInterfaces();
        long end = System.currentTimeMillis();
        long useTime = end - start;
        if (null != interfaces && interfaces.length > 0 && interfaces[0].isAnnotationPresent(FeignClient.class)) {
            interfaceName = interfaces[0].getName();
            feignLogger.info("className: {}; method: {}; args:{}; return: {}; useTime: {}",
                    interfaceName, methodName, GsonUtil.toJson(args),
                    GsonUtil.toJson(result), useTime);
        } else {
            opLogger.info("className: {}; method: {}; args:{}; return: {}; useTime: {}",
                    interfaceName, methodName, GsonUtil.toJson(args),
                    GsonUtil.toJson(result), useTime);
        }
        return result;

    }

}
