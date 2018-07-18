package com.ns.common.util.ucloud;

import com.ns.common.bean.Ucloud;
import com.ns.common.util.constant.UCloudConstant;
import com.ns.common.util.http.HttpUtil;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class FileAction {
    /**
     * 上传文件(put)
     *
     * @param bucket
     * @param saveKey
     * @param file
     * @param contentType
     * @return
     */
    public static String putFile(Ucloud ucloud, String bucket, String saveKey, File file, String contentType) {
        String url = MessageFormat.format(UCloudConstant.BASE_PUT_URL, bucket, saveKey);
        String authorization = SigUtil.getAuthorization(contentType, UCloudConstant.HTTP_PUT_METHOD, ucloud, bucket, saveKey);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", contentType);
        headers.put(UCloudConstant.ParamKey.AUTHORIZATION, authorization);
        String result = HttpUtil.putFile(url, headers, file);
        return result;
    }

    /**
     * 上传文件(put)
     *
     * @param bucket
     * @param saveKey
     * @param bytes
     * @param contentType
     * @return
     */
    public static String putFile(Ucloud ucloud, String bucket, String saveKey, byte[] bytes, String contentType) {
        String url = MessageFormat.format(UCloudConstant.BASE_PUT_URL, bucket, saveKey);
        String authorization = SigUtil.getAuthorization(contentType, UCloudConstant.HTTP_PUT_METHOD, ucloud, bucket, saveKey);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", contentType);
        headers.put("Content-Length", String.valueOf(bytes.length));
        headers.put(UCloudConstant.ParamKey.AUTHORIZATION, authorization);
        String result = HttpUtil.putFile(url, headers, bytes);
        return result;
    }

    /**
     * 上传文件(post)
     *
     * @param bucket
     * @param saveKey
     * @param file
     * @param contentType
     * @return
     */
    public static String postFile(Ucloud ucloud, String bucket, String saveKey, File file, String contentType) {
        String url = MessageFormat.format(UCloudConstant.BASE_POST_URL, bucket, saveKey);
        // set params
        String authorization = SigUtil.getAuthorization(contentType, UCloudConstant.HTTP_POST_METHOD, ucloud, bucket, saveKey);
        Map<String, String> headers = new HashMap<>();
        //设置headers
        headers.put(UCloudConstant.ParamKey.AUTHORIZATION, authorization);
        Map<String, Object> partsMap = new HashMap<>();
        partsMap.put(UCloudConstant.ParamKey.FILENAME, saveKey);
        partsMap.put(UCloudConstant.ParamKey.AUTHORIZATION, authorization);
        partsMap.put("file", file);
        String result = HttpUtil.postFiles(url, headers, partsMap);
        return result;
    }
}
