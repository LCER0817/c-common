package com.ns.common.util.constant;

/**
 * Created  liqiuwei on 2017/6/8.
 */
public interface ParamConstant {
    int CACHE_MAX_SIZE = 1000;
    int CACHE_EXPIRE = 300;

    interface ValidStatus {
        Integer INVALID = 0;
        Integer VALID = 1;

        Integer[] ALL = {
                INVALID, VALID
        };
    }

}
