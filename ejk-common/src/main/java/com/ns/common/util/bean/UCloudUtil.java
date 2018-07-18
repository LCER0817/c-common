package com.ns.common.util.bean;

import com.ns.common.bean.Ucloud;
import com.ns.common.util.constant.UCloudConstant;
import org.apache.commons.lang.StringUtils;

public class UCloudUtil {

    public static Ucloud getUCloud(String publicKey, String privateKey) {
        return getUCloud(publicKey, privateKey, null);
    }

    public static Ucloud getUCloud(String publicKey, String privateKey, String serverUrl) {
        Ucloud ucloud;
        if (StringUtils.isNotEmpty(serverUrl)) {
            ucloud = new Ucloud(publicKey, privateKey, serverUrl);
        } else {
            ucloud = new Ucloud(publicKey, privateKey, UCloudConstant.BASE_API_URL);
        }
        return ucloud;
    }

}
