package com.ns.common.util.constant;

import com.google.common.base.Charsets;

import java.nio.charset.Charset;

public interface UCloudConstant {
    Charset CHARSET = Charsets.UTF_8;
    String BASE_API_URL = "https://api.ucloud.cn";
    String BASE_PUT_URL = "http://{0}.ufile.ucloud.cn/{1}";
    String BASE_POST_URL = "http://{0}.ufile.ucloud.cn/{1}";

    String HTTP_PUT_METHOD = "PUT";
    String HTTP_POST_METHOD = "POST";

    interface Action {
        String CREATE_BUCKET = "CreateBucket";// 创建bucket
        String DESCRIBE_BUCKET = "DescribeBucket";// 获取bucket信息
        String UPDATE_BUCKET = "UpdateBucket";// 更新bucket属性
        String DELETE_BUCKET = "DeleteBucket";// 删除bucket
        String GET_FILE_LIST = "GetFileList";// 获取文件列表
    }

    interface ParamKey {
        String AUTHORIZATION = "Authorization";
        String TYPE = "Type";
        String ACTION = "Action";
        String BUCKNAME = "BucketName";
        String FILENAME = "FileName";
    }
}
