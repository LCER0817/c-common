package com.ns.common.util.Lnglat;


import com.ns.common.util.exception.sys.ParameterException;

/**
 * Created by xuezhucao on 16/7/1.
 */
public class LngLatUtil {
    public static long adjustChineseLongitude(long longitude) throws Throwable {
        if (longitude <= 0) {
            throw new ParameterException("无效的经度");
        }
        if ((longitude / 10 ^ 6) < 10) {
            longitude *= 10;
        }
        return longitude;
    }

    public static long adjustChineseLatitude(long latitude) throws Throwable {
        if (latitude <= 0) {
            throw new ParameterException("无效的纬度");
        }
        if ((latitude / 10 ^ 6) < 10) {
            latitude *= 10;
        }
        return latitude;
    }
}
