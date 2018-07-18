package com.ns.common.util.ucloud;

import com.google.common.collect.Maps;
import com.ns.common.bean.Ucloud;
import com.ns.common.util.constant.UCloudConstant;

import java.util.Map;

public class BucketAction {
    /**
     * 创建bucket
     *
     * @param bucketName 1、仅包含小写字母，数字和连字符（-）
     *                   2、必须以小写字母或者数字开头
     *                   3、长度必须在6-63字节之间。
     * @return
     */
    public static String createBucket(Ucloud ucloud, String bucketName) {
        return UCloudCommander.sendAction(ucloud, UCloudConstant.Action.CREATE_BUCKET, bucketName);
    }

    public static String createBucket(Ucloud ucloud, String bucketName, Map<String, String> params) {
        return UCloudCommander.sendAction(ucloud, UCloudConstant.Action.CREATE_BUCKET, bucketName, params);
    }

    /**
     * 删除bucket
     *
     * @param bucketName
     * @return
     */
    public static String deleteBucket(Ucloud ucloud, String bucketName) {
        return UCloudCommander.sendAction(ucloud, UCloudConstant.Action.DELETE_BUCKET, bucketName);
    }

    /**
     * 更新bucket属性
     *
     * @param bucketName
     * @param type
     * @return
     */
    public static String updateBucket(Ucloud ucloud, String bucketName, String type) {
        Map<String, String> params = Maps.newHashMap();
        params.put(UCloudConstant.ParamKey.TYPE, type.toLowerCase());
        return UCloudCommander.sendAction(ucloud, UCloudConstant.Action.UPDATE_BUCKET, bucketName, params);
    }

    /**
     * 获取bucket详情
     *
     * @param ucloud
     * @param bucketName
     * @return
     */
    public static String getBucketInfo(Ucloud ucloud, String bucketName) {
        return UCloudCommander.sendAction(ucloud, UCloudConstant.Action.DESCRIBE_BUCKET, bucketName);
    }
}
