/**
 * $Id: HttpUtil.java 71814 2012-03-13 10:15:30Z xuezhu.cao@XIAONEI.OPI.COM $
 * Copyright 2009-2011 Oak Pacific Interactive. All rights reserved.
 */
package com.ns.common.util.http;

import com.ns.common.util.constant.HttpClientConstant;
import com.ns.common.util.exception.errorcode.CommonErrorCode;
import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.exception.sys.SystemInternalException;
import com.ns.common.util.gson.GsonUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.methods.multipart.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author xuezhu.cao Initial Created at 2011-10-27
 */
public class HttpUtil {
    public static final String CONTENT_KEY = "content";
    public static final String COOKIES_KEY = "cookies";
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private volatile static HttpClient client = null;

    protected static HttpClient getHttpClient() {
        if (client == null) {
            synchronized (HttpUtil.class) {
                if (client == null) {
                    client = new HttpClient(new MultiThreadedHttpConnectionManager());

                    client.getParams().setIntParameter("http.socket.timeout", HttpClientConstant.HTTP_SOCKET_TIMEOUT);
                    client.getHttpConnectionManager().getParams().setConnectionTimeout(HttpClientConstant.HTTP_CONNECTION_TIMEOUT);
                    client.getHttpConnectionManager().getParams().setSoTimeout(HttpClientConstant.HTTP_SO_TIMEOUT);
                    client.getHttpConnectionManager().getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, HttpClientConstant.HTTP_CONTENT_CHARSET);
                    client.getHttpConnectionManager().getParams().setBooleanParameter("http.protocol.expect-continue", false);
                }
            }
        }
        return client;
    }

    public static String get(String url, Map<String, String> params) throws IOException, NSException {
        StringBuilder builder = new StringBuilder(100);
        builder.append(url);
        appendRequestString(builder, params);
        return request(builder.toString());
    }

    public static String get(String url, Object... params) throws IOException, NSException {
        StringBuilder builder = new StringBuilder(100);
        builder.append(url);
        appendRequestString(builder, params);
        return request(builder.toString());
    }

    public static String post(String url, Object... params) throws IOException, NSException {
        PostMethod postMethod = new UTF8PostMethod(url);
        postMethod.setRequestBody(getNameValuePairs(params));
        return request(postMethod);
    }

    /**
     * @param url
     * @param headers  header中的参数
     * @param paramMap
     * @return
     */
    public static String post(String url, Map<String, String> headers, Map<String, Object> paramMap) {
        PostMethod postMethod = new UTF8PostMethod(url);
        setHeaders(postMethod, headers);
        // 判断是否设置了Content-Type，如果为application/json，修改放置方法
        if (MapUtils.isNotEmpty(headers) && headers.containsKey("Content-Type") && headers.get("Content-Type").equals("application/json")) {
            postMethod.setRequestBody(GsonUtil.toJson(paramMap));
        } else {
            postMethod.setRequestBody(getNameValuePairs(paramMap));
        }

        try {
            return request(postMethod);
        } catch (Exception e) {
            logger.error("", e);
            throw new SystemInternalException();
        }
    }

    /**
     * 上传文件
     *
     * @param url
     * @param headers
     * @param partsMap
     * @return
     */
    public static String postFiles(String url, Map<String, String> headers, Map<String, Object> partsMap) {
        PostMethod postMethod = new UTF8PostMethod(url);
        setHeaders(postMethod, headers);
        postMethod.setRequestHeader("Connection", "close");
        try {
            postMethod.setRequestEntity(getMultiRequestEntity(postMethod, partsMap));
            return request(postMethod);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    public static String getClientCookies() {
        Cookie[] cookies = client.getState().getCookies();
        StringBuffer buffer = new StringBuffer();
        for (Cookie c : cookies) {
            buffer.append(c.toString() + ";");
        }
        return buffer.toString();
    }

    public static String put(String url, Map<String, String> headers, Map<String, Object> paramMap) {
        PutMethod putMethod = new PutMethod(url);
        setHeaders(putMethod, headers);
        putMethod.setRequestBody(GsonUtil.toJson(paramMap));
        try {
            return request(putMethod);
        } catch (IOException e) {
            logger.error("%s",e);
        }
        return null;
    }

    public static String putFile(String url, Map<String, String> headers, Object entity) {
        PutMethod putMethod = new PutMethod(url);
        setHeaders(putMethod, headers);
        try {
            putMethod.setRequestEntity(getRequestEntity(entity));
            return request(putMethod);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    public static String request(String url) throws IOException, NSException {
        if (logger.isDebugEnabled()) {
            logger.debug("get url: " + url);
        }
        GetMethod getMethod = new GetMethod(url);
        return request(getMethod);
    }

    private static String request(HttpMethod method) throws IOException, NSException {
        try {
            HttpClient httpClient = getHttpClient();

            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, HttpClientConstant.CHARSET);
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, HttpClientConstant.HTTP_SO_TIMEOUT);

            int statusCode = httpClient.executeMethod(method);
            String content = request(method.getResponseBodyAsStream());
            if (statusCode != HttpStatus.SC_OK) {
                logger.warn(String.format("statusCode: %s; content %s", statusCode, content));
                throw new NSException(CommonErrorCode.HTTP_EXCEPTION, statusCode);
            }

            if (logger.isDebugEnabled()) {
                logger.debug("content: " + content);
            }

            return content;
        } catch (HttpException e) {
            logger.error("", e);
            throw e;
        } catch (NSException e) {
            logger.error("", e);
            throw e;
        } finally {
            method.releaseConnection();
        }
    }

    private static String request(InputStream is) throws IOException {
        InputStreamReader reader = null;

        try {
            char[] chars = new char[HttpClientConstant.HTTP_READ_BUFFER_SIZE];
            int length;
            reader = new InputStreamReader(is, HttpClientConstant.CHARSET);

            StringBuffer buffer = new StringBuffer(500);
            while ((length = reader.read(chars, 0, HttpClientConstant.HTTP_READ_BUFFER_SIZE)) != -1) {
                buffer.append(chars, 0, length);
            }

            return buffer.toString();
        } catch (IOException e) {
            logger.error("", e);
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
    }

    private static void setHeaders(HttpMethod method, Map<String, String> headers) {
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                method.setRequestHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private static void appendRequestString(StringBuilder builder, Map<String, String> params) {
        if (MapUtils.isNotEmpty(params)) {
            builder.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            builder.setLength(builder.length() - 1);
        }
    }

    private static void appendRequestString(StringBuilder builder, Object... params) throws UnsupportedEncodingException {
        if (params != null && params.length > 1) {
            builder.append("?");
            for (int i = 0; i < params.length; i += 2) {
                if (i + 1 >= params.length) {
                    break;
                }
                builder.append(URLEncoder.encode(params[i].toString(), HttpClientConstant.CHARSET)).append("=")
                        .append(URLEncoder.encode(params[i + 1].toString(), HttpClientConstant.CHARSET)).append("&");
            }
            builder.setLength(builder.length() - 1);
        }
    }

    private static NameValuePair[] getNameValuePairs(Object... params) {
        NameValuePair[] result = null;
        if (params != null && params.length > 1) {
            result = new NameValuePair[params.length / 2];
            NameValuePair pair;
            for (int i = 0; i < params.length; i += 2) {
                if (i + 1 >= params.length) {
                    break;
                }
                pair = new NameValuePair();
                pair.setName(params[i].toString());
                pair.setValue(params[i + 1].toString());
                result[i / 2] = pair;
            }
        }
        return result;
    }

    private static NameValuePair[] getNameValuePairs(Map<String, Object> paramMap) {
        NameValuePair[] result = null;
        if (paramMap != null && paramMap.size() > 0) {
            result = new NameValuePair[paramMap.size()];
            NameValuePair pair;
            int index = 0;
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                pair = new NameValuePair(entry.getKey(), entry.getValue().toString());
                result[index] = pair;
                index++;
            }
        }
        return result;
    }

    private static RequestEntity getRequestEntity(Object entity) {
        RequestEntity result;
        if (entity instanceof byte[]) {
            result = new ByteArrayRequestEntity((byte[]) entity);
        } else if (entity instanceof File) {
            File file = (File) entity;
            result = new FileRequestEntity((File) entity, file.getName());
        } else if (entity instanceof InputStream) {
            result = new InputStreamRequestEntity((InputStream) entity);
        } else {
            result = new StringRequestEntity(entity.toString());
        }
        return result;
    }

    private static MultipartRequestEntity getMultiRequestEntity(EntityEnclosingMethod method, Map<String, Object> map) throws FileNotFoundException {
        Part[] parts = new Part[] {};
        if (MapUtils.isNotEmpty(map)) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof File) {
                    parts = (Part[]) ArrayUtils.add(parts,
                            new FilePart(entry.getKey(), (File) entry.getValue()));
                } else if (entry.getValue() instanceof ByteArrayPartSource) {
                    parts = (Part[]) ArrayUtils.add(parts,
                            new FilePart(entry.getKey(), (ByteArrayPartSource) entry.getValue()));
                } else {
                    parts = (Part[]) ArrayUtils.add(parts,
                            new StringPart(entry.getKey(), entry.getValue().toString(), "UTF-8"));
                }
            }
        }
        return new MultipartRequestEntity(parts, method.getParams());
    }
}
