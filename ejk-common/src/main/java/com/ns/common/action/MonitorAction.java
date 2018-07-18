package com.ns.common.action;

import com.google.common.collect.Maps;
import com.ns.common.util.annotation.IgnoreAopNSJsonHandler;
import com.ns.common.util.annotation.IgnoreLog;
import com.ns.common.util.gson.GsonUtil;
import org.springframework.boot.actuate.endpoint.mvc.HealthMvcEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import java.util.Properties;

public class MonitorAction {

    private static String codeUrl;
    private static String codeVersion;

    static {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties("codeversion.properties");
            codeUrl = (String) properties.get("codeUrl");
            codeVersion = (String) properties.get("codeVersion");
        } catch (Exception e) {
            codeUrl = "error";
            codeVersion = "error";
        }
    }

    @Resource
    private HealthMvcEndpoint endpoint;

    @PostMapping("/${spring.application.name}/monitor")
    @IgnoreLog
    @IgnoreAopNSJsonHandler
    public Object monitor(HttpServletRequest request, Principal principal) {

        Map<String, Object> result = Maps.newHashMap();
        int errorCode = 0;
        boolean errorFlag = false;
        Object info = null;
        try {
            info = endpoint.invoke(request, principal);
            Health health = null;
            if (info instanceof ResponseEntity) {
                ResponseEntity<Health> entity = (ResponseEntity) info;
                health = entity.getBody();
            } else if (info instanceof Health) {
                health = (Health) info;
            }
            if (null != health) {
                if (!Status.UP.equals(health.getStatus())) {
                    Map<String, Object> details = health.getDetails();
                    info = details;
                    for (Map.Entry<String, Object> detail : details.entrySet()) {
                        String component = detail.getKey();
                        Object value = detail.getValue();
                        if (value instanceof Health) {
                            Health moduleHealth = (Health) value;
                            Status status = moduleHealth.getStatus();
                            if (Status.UP != status) {
                                errorFlag = true;
                                break;
                            }
                        }
                    }
                }
                if (!errorFlag) {
                    info = "success";
                }
            }
        } catch (Exception e) {
            errorCode = 1;
        }
        if (errorFlag) {
            errorCode = 2;
        }
        result.put("errorCode", errorCode);
        result.put("errorMessage", GsonUtil.toJson(info));
        result.put("codeUrl", codeUrl);
        result.put("codeVersion", codeVersion);
        return result;
    }
}
