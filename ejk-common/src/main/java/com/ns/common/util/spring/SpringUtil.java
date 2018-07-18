package com.ns.common.util.spring;

import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.exception.sys.ParameterException;
import com.ns.common.util.exception.sys.SystemInternalException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by xuezhucao on 16/6/20.
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static Logger logger = Logger.getLogger(SpringUtil.class);
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        logger.info("application context set: " + arg0);
        context = arg0;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static <T> T getBean(Class<T> clazz) throws NSException {
        if (clazz == null) {
            logger.warn("bean clazz is null");
            throw new ParameterException("类名为空");
        }
        if (context == null) {
            logger.warn("spring context is null");
            throw new SystemInternalException();
        }
        T result = getApplicationContext().getBean(clazz);
        if (result == null) {
            logger.warn("could not find bean of class " + clazz.getName());
            throw new SystemInternalException();
        }
        return result;
    }

}
