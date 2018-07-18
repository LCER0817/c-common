package com.ns.common.util.constant;

/**
 * Created by caoxuezhu01 on 15-3-17.
 */
public interface HttpClientConstant {
    String CHARSET = "utf-8";
    int HTTP_READ_BUFFER_SIZE = 4096;
    int HTTP_CONNECTION_TIMEOUT = 3000;
    int HTTP_SO_TIMEOUT = 3000;
    int HTTP_SOCKET_TIMEOUT = 3000;
    String HTTP_CONTENT_CHARSET = "utf-8";

    interface Method {
        int GET = 1;
        int POST = 2;
    }
}
