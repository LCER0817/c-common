package com.ns.common.util.ucloud;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.ns.common.bean.Ucloud;
import com.ns.common.util.constant.UCloudConstant;
import org.apache.commons.codec.digest.HmacUtils;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SigUtil {

    /**
     * 空间操作签名
     *
     * @param params
     * @param privateKey
     * @return
     */
    public static String spaceSign(Map<String, String> params, String privateKey) {
        StringBuilder sb = new StringBuilder();
        List<String> keyList = Lists.newArrayList(params.keySet());
        Collections.sort(keyList);
        keyList.stream().forEach(key -> sb.append(key).append(params.get(key)));
        sb.append(privateKey);
        return Hashing.sha1().hashString(sb.toString(), UCloudConstant.CHARSET).toString();
    }

    /**
     * 插入签名参数
     *
     * @param params
     * @return
     */
    public static Map<String, String> spaceSignParams(Map<String, String> params, String publicKsy, String pirvateKey) {
        params.put("PublicKey", publicKsy);
        params.put("Signature", SigUtil.spaceSign(params, pirvateKey));
        return params;
    }

    /**
     * 获取授权串
     *
     * @param contentType
     * @param method
     * @param ucloud
     * @param bucket
     * @param saveKey
     * @return
     */
    public static String getAuthorization(String contentType, String method, Ucloud ucloud, String bucket, String saveKey) {
        return "UCloud" + " " + ucloud.getPublicKey() + ":" + fileSign(contentType, method, bucket, saveKey, ucloud.getPrivateKey());
    }

    /**
     * 文件操作签名
     *
     * @param contentType
     * @param method
     * @param bucket
     * @param saveKey
     * @param privateKey
     * @return
     */
    public static String fileSign(String contentType, String method, String bucket, String saveKey, String privateKey) {
        String str2Sign = new StringBuilder()
                .append(method).append("\n")
                .append("").append("\n")
                .append(contentType).append("\n")
                .append("").append("\n")
                .append(canonicalizedResource(bucket, saveKey))
                .toString();
        return Base64.getEncoder().encodeToString(HmacUtils.hmacSha1(privateKey, str2Sign));
    }

    private static String canonicalizedResource(String bucket, String key) {
        return new StringBuffer()
                .append("/")
                .append(bucket)
                .append("/")
                .append(key)
                .toString();
    }

}

