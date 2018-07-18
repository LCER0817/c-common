package com.ns.common.util.ucloud;

import com.google.common.collect.Maps;
import com.ns.common.bean.Ucloud;
import com.ns.common.util.constant.UCloudConstant;
import com.ns.common.util.http.HttpUtil;

import java.util.Map;

public class UCloudCommander {

    public static String sendAction(Ucloud ucloud, String action, String bucketName) {
        Map<String, String> params = Maps.newHashMap();
        return sendAction(ucloud, action, bucketName, params);
    }

    public static String sendAction(Ucloud ucloud, String action, String bucketName, Map<String, String> params) {
        params.put(UCloudConstant.ParamKey.ACTION, action);
        params.put(UCloudConstant.ParamKey.BUCKNAME, bucketName);
        params = SigUtil.spaceSignParams(params, ucloud.getPublicKey(), ucloud.getPrivateKey());
        try {
            String content = HttpUtil.get(UCloudConstant.BASE_API_URL, params);
            return content;
        } catch (Exception e) {
            return null;
        }
    }
}
