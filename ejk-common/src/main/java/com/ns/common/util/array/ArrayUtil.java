package com.ns.common.util.array;

import org.apache.commons.lang.ArrayUtils;

/**
 * Created by xuezhucao on 16/7/1.
 */
public class ArrayUtil {
    public static String[] addAll(String[] array1, String[] array2) {
        Object[] objs = ArrayUtils.addAll(array1, array2);
        String[] result = null;
        if (ArrayUtils.isNotEmpty(objs)) {
            result = new String[objs.length];
            for (int i = 0; i < objs.length; i++) {
                result[i] = (String) objs[i];
            }
        }
        return result;
    }
}
