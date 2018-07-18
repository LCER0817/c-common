package com.ns.common.aop;

import com.ns.common.util.exception.sys.DataBaseOperationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AopNSDaoHandler {

    private static Logger logger = LoggerFactory.getLogger(AopNSDaoHandler.class);

    public static Object handle(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            logger.warn("", e);
            throw new DataBaseOperationException();
        }
    }

}
