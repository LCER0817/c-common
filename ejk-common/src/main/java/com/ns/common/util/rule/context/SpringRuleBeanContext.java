package com.ns.common.util.rule.context;

import com.ns.common.util.exception.sys.ParameterException;
import com.ns.common.util.rule.IRuleBiz;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringRuleBeanContext implements ApplicationContextAware, RuleBeanContext {

    // 让spring容器注入或者自己new都可以使用
    private static ApplicationContext applicationContext;

    @Override
    public IRuleBiz getBean(String vName) {
        Object bean = applicationContext.getBean(vName);
        if (null != bean && bean instanceof IRuleBiz) {
            return (IRuleBiz) bean;
        } else {
            throw new ParameterException("spring context不存在名字为: " + vName + ", 类型为: IRuleBiz的bean");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringRuleBeanContext.applicationContext = applicationContext;
    }

}
