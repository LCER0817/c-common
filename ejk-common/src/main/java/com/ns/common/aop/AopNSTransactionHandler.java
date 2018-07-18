package com.ns.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class AopNSTransactionHandler {

    private static Logger logger = LoggerFactory.getLogger(AopNSTransactionHandler.class);

    public static Object handle(ProceedingJoinPoint joinPoint, JpaTransactionManager transactionManager) throws Throwable {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        Object object = null;
        try {
            object = joinPoint.proceed();
            transactionManager.commit(status);
        } catch (Throwable e) {
            logger.warn("事务提交失败！", e);
            transactionManager.rollback(status);
            throw e;
        }
        return object;
    }

}
