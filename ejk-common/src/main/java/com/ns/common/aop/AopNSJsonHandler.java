package com.ns.common.aop;

import com.ns.common.util.annotation.IgnoreLog;
import com.ns.common.util.exception.errorcode.CommonErrorCode;
import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.exception.sys.SystemInternalException;
import com.ns.common.util.gson.GsonUtil;
import com.ns.common.util.log.LoggerUtil;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuezhucao on 2017/9/21.
 */
public class AopNSJsonHandler {

    private static Logger logger = LoggerFactory.getLogger(AopNSJsonHandler.class);
    private static final Logger opLogger = LoggerUtil.getOpLog();

    public static Object handle(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        String interfaceName = joinPoint.getTarget().getClass().getName();
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        Map<String, Object> result = new HashMap<String, Object>(2);
        Map<String, Object> head = new HashMap<>(2);
        int errCode = 0;
        String errMsg = "successful";
        Object data;
        try {
            data = joinPoint.proceed();
        } catch (Throwable e) {
            logger.warn("", e);
            NSException e1;
            if (e instanceof NSException) {
                e1 = (NSException) e;
                errCode = e1.getCode();
                errMsg = e1.getMsg();
            } else if (StringUtils.isNotEmpty(e.getMessage())) {
                e1 = new NSException(CommonErrorCode.UNKOWN_EXCEPTION);
                errCode = e1.getCode();
                errMsg = e.getMessage();
            } else {
                e1 = new SystemInternalException();
                errCode = e1.getCode();
                errMsg = e1.getMsg();
            }
            data = null;
        }
        head.put("errCode", errCode);
        head.put("errMsg", errMsg);
        result.put("head", head);
        result.put("data", data);
        long end = System.currentTimeMillis();
        long useTime = end - start;
        // 不打印一些特殊请求，如文件上传
        if ((signature instanceof MethodSignature)) {
            MethodSignature msig = (MethodSignature) signature;
            Method method = msig.getMethod();
            if (!method.isAnnotationPresent(IgnoreLog.class)) {
                opLogger.info("className: {}; method: {}; args:{}; return: {}; useTime: {}", interfaceName, methodName, GsonUtil.toJson(joinPoint.getArgs()),
                        GsonUtil.toJson(result), useTime);
            }
        }
        return result;
    }
}
