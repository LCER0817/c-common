package com.ns.common.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xuezhucao on 2017/10/8.
 */
public class LoggerUtil {
    public static Logger getLogger(Class clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Logger getOpLog() {
        return LoggerFactory.getLogger("op");
    }

    public static Logger getFeignLog() {
        return LoggerFactory.getLogger("feign");
    }

    public static Logger getThirdLog() {
        return LoggerFactory.getLogger("3rd");
    }
}
