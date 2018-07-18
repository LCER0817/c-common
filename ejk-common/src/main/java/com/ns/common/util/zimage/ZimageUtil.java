package com.ns.common.util.zimage;

import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.exception.sys.SystemInternalException;
import com.ns.common.util.gson.GsonUtil;
import com.ns.common.util.http.HttpUtil;
import com.ns.common.util.http.SslUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZimageUtil {
    private static Logger logger = LoggerFactory.getLogger(ZimageUtil.class);

    public static String uploadImg(HttpServletRequest request, String uploadUrl) throws NSException {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        commonsMultipartResolver.setDefaultEncoding("utf-8");

        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            List<MultipartFile> files = new ArrayList<>();
            Iterator<String> it = multiRequest.getFileNames();
            while (it.hasNext()) {
                MultipartFile file = multiRequest.getFile(it.next());
                files.add(file);
            }

            return ZimageUtil.upload(files, uploadUrl);
        } else {
            Map<String, Object> paramsMap = GsonUtil.fromJson(ZimageUtil.getRequestBody(request), Map.class);

            if (!paramsMap.containsKey("photo")) {
                throw new SystemInternalException();
            }

            //判断photo是否为数组
            Map<String, byte[]> params = new HashMap<>();
            Object photo = paramsMap.get("photo");
            if (photo instanceof List) {
                List<String> photosList = (List<String>) photo;
                for (String base64 : photosList) {
                    params.putAll(handleBaseImg(base64));
                }
            } else if (photo instanceof String) {
                String base64 = photo.toString();
                params.putAll(handleBaseImg(base64));

            }
            return ZimageUtil.upload(params, uploadUrl);
        }
    }

    private static Map<String, byte[]> handleBaseImg(String base64) throws NSException {
        String data;
        String dataPrix;
        if (StringUtils.isEmpty(base64)) {
            throw new SystemInternalException();
        }
        String[] d = base64.split("base64,");
        if (d != null && d.length == 2) {
            dataPrix = d[0];
            data = d[1];
        } else {
            throw new SystemInternalException();
        }
        String suffix;
        if ("data:image/jpeg;".equalsIgnoreCase(dataPrix) || "data:image/jpg;".equalsIgnoreCase(dataPrix)) {
            suffix = ".jpg";
        } else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) {
            suffix = ".ico";
        } else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {
            suffix = ".gif";
        } else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {
            suffix = ".png";
        } else {
            throw new SystemInternalException();
        }
        Map<String, byte[]> result = new HashMap<>();
        result.put(UUID.randomUUID().toString() + suffix, Base64.decodeBase64(data));
        return result;
    }

    /**
     * 上传照片
     *
     * @throws Exception
     */
    public static String upload(List<MultipartFile> multipartFiles, String url) throws NSException {
        try {
            Map<String, Object> partsMap = new HashMap<>(1);
            if (CollectionUtils.isEmpty(multipartFiles)) {
                return null;
            }
            int i = 0;
            for (MultipartFile file : multipartFiles) {
                ByteArrayPartSource source = new ByteArrayPartSource(file.getOriginalFilename(), file.getBytes());
                partsMap.put("photo" + i, source);
                i++;
            }
            String responseBody = HttpUtil.postFiles(url, null, partsMap);
            // 返回结果为html
            if (StringUtils.isEmpty(responseBody)) {
                logger.warn("upload photo fail: " + url);
                throw new SystemInternalException();
            }
            String md5 = getValueFromHtml(responseBody);
            if (StringUtils.isEmpty(md5)) {
                logger.warn("upload photo fail: " + url);
                throw new SystemInternalException();
            } else {
                return md5;
            }
        } catch (IOException e) {
            throw new SystemInternalException();
        }
    }

    public static String upload(Map<String, byte[]> imgs, String url) throws NSException {
        if (MapUtils.isEmpty(imgs)) {
            return null;
        }
        Map<String, Object> partsMap = new HashMap<>(1);
        int i = 0;
        for (String s : imgs.keySet()) {
            partsMap.put("photo" + i, new ByteArrayPartSource(s, imgs.get(s)));
            i++;
        }

        String responseBody = HttpUtil.postFiles(url, null, partsMap);
        String md5 = getValueFromHtml(responseBody);
        if (StringUtils.isEmpty(md5)) {
            logger.warn("upload photo fail: " + url);
            throw new SystemInternalException();
        } else {
            return md5;
        }
    }

    private static String getValueFromHtml(String msg) {
        Pattern p = Pattern.compile("<a href=\"[^\"]+\"");
        Matcher m = p.matcher(msg);
        String[] tmp = new String[4];
        int i = 0;
        while (m.find()) {
            tmp[i] = m.group(0);
            i++;
        }
        if (tmp == null || tmp.length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : tmp) {
            if (StringUtils.isNotEmpty(s)) {
                sb.append(s.replace("<a href=\"/", "").replace("\"", ""));
                sb.append(",");
            }
        }
        if (sb.length() <= 0) {
            return null;
        }
        String md5 = sb.substring(0, sb.length() - 1);
        return md5;
    }

    public static String getRequestBody(HttpServletRequest req) throws NSException {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = req.getReader();
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            throw new SystemInternalException();
        }
        return sb.toString();
    }

    public static byte[] getByteFromUrl(String photoUrl) throws NSException {
        byte[] fileBytes;
        try {
            URL url = new URL(photoUrl);
            if ("https".equalsIgnoreCase(url.getProtocol())) {
                SslUtils.ignoreSsl();
            }
            InputStream in = url.openStream();
            fileBytes = IOUtils.toByteArray(in);
        } catch (Exception e) {
            throw new SystemInternalException();
        }
        if (ArrayUtils.isEmpty(fileBytes)) {
            throw new SystemInternalException();
        }
        return fileBytes;
    }

    public static Map<String, byte[]> getMapFromUrl(String photoUrl) throws NSException {
        Map<String, byte[]> map = new HashMap<>(1);
        byte[] bytes = getByteFromUrl(photoUrl);
        String fileCode = Hex.encodeHexString(bytes);
        String suffix;
        if (fileCode.toLowerCase().startsWith("ffd8ff")) {//JPEG (jpg)
            suffix = ".jpg";
        } else if (fileCode.toLowerCase().startsWith("89504e47")) {//PNG (png)
            suffix = ".png";
        } else if (fileCode.toLowerCase().startsWith("47494638")) {//GIF (gif)
            suffix = ".gif";
        } else if ((fileCode.toLowerCase().startsWith("424d"))) {//(bmp)
            suffix = ".bmp";
        } else {
            throw new SystemInternalException();
        }
        map.put(UUID.randomUUID().toString() + suffix, bytes);
        return map;
    }
}
