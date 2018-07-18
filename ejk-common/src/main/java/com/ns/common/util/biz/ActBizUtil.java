package com.ns.common.util.biz;

import com.google.common.collect.Lists;
import com.ns.common.bean.ActBizObj;
import com.ns.common.util.constant.CharConstant;
import com.ns.common.util.exception.errorcode.CommonErrorCode;
import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.exception.sys.ParameterException;
import com.ns.common.util.gson.GsonUtil;
import com.ns.common.util.string.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiuwei on 2017/8/25.
 */
public class ActBizUtil {

    private static final String[] DEFUALT_IGNORE_KEY = {"class", "empty"};


    /**
     * 将bean转换为map，动态扩展vo属性时使用
     *
     * @param object
     * @return
     */
    public static Map<String, Object> toMap(Object object, String[] keys, Object[] values) throws NSException {
        return toMap(object, keys, values, DEFUALT_IGNORE_KEY);
    }

    public static Map<String, Object> toMap(Object object, String[] keys, Object[] values, String... ignoreKeys) throws NSException {
        Map<String, Object> dataMap;
        if (!ArrayUtils.isSameLength(keys, values)) {
            throw new ParameterException("参数数据量不匹配");
        }
        try {
            dataMap = PropertyUtils.describe(object);
            for (int i = 0; i < keys.length; i++) {
                dataMap.put(keys[i], values[i]);
            }
            if (ArrayUtils.isNotEmpty(ignoreKeys)) {
                for (String igKey : ignoreKeys) {
                    dataMap.remove(igKey);
                }
            }
        } catch (Exception e) {
            throw new NSException(CommonErrorCode.OPERATIOIN_FAIL);
        }
        return dataMap;
    }

    public static Map<String, Object> toMap(Object object, Object... entries) throws NSException {
        return toMap(DEFUALT_IGNORE_KEY, object, entries);
    }

    public static Map<String, Object> toMap(String[] ignoreKeys, Object object, Object... entries) throws NSException {
        if (object == null) {
            throw new ParameterException("对象为空");
        }
        if (entries == null) {
            throw new ParameterException("参数数据为空");
        }
        Map<String, Object> dataMap;
        if (entries.length % 2 != 0) {
            throw new ParameterException("参数数据量不匹配");
        }
        try {
            dataMap = PropertyUtils.describe(object);
            for (int i = 0; i < entries.length; i += 2) {
                dataMap.put((String) entries[i], entries[i + 1]);
            }
            if (ArrayUtils.isNotEmpty(ignoreKeys)) {
                for (String igKey : ignoreKeys) {
                    dataMap.remove(igKey);
                }
            }
        } catch (Exception e) {
            throw new NSException(CommonErrorCode.OPERATIOIN_FAIL);
        }
        return dataMap;
    }

    public static Map<String, Object> toMap(Object object, Map<String, Object> map, String... ignoreKeys) throws NSException {
        Map<String, Object> dataMap;
        try {
            dataMap = PropertyUtils.describe(object);
            dataMap.remove("class");
            if (MapUtils.isNotEmpty(map)) {
                dataMap.putAll(map);
            }
            if (ArrayUtils.isNotEmpty(ignoreKeys)) {
                for (String igKey : ignoreKeys) {
                    dataMap.remove(igKey);
                }
            }
        } catch (Exception e) {
            throw new NSException(CommonErrorCode.OPERATIOIN_FAIL);
        }
        return dataMap;
    }


    public static Map<String, Object> toActBizObjMap(ActBizObj actBizObj) throws NSException {
        if (actBizObj == null) {
            return null;
        }
        return toMap(actBizObj.getObj(), actBizObj.getDataMap(), null);
    }


    public static <T> List<Map<String, Object>> toActBizObjList(List<ActBizObj<T>> actBizObjs) throws NSException {
        if (CollectionUtils.isEmpty(actBizObjs)) {
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>(actBizObjs.size());
        actBizObjs.forEach(actBizObj -> list.add(toActBizObjMap(actBizObj)));
        return list;
    }

    /**
     * 直接将相关对象进行字符串转化
     *
     * @param object
     * @param objs
     * @param <T>
     * @return
     * @throws NSException
     */

    public static <T> String toJsonString(Object object, T... objs) throws NSException {
        List<String> keys = Lists.newArrayListWithCapacity(objs.length);
        List<Object> values = Lists.newArrayListWithCapacity(objs.length);
        for (T obj : objs) {
            if (obj.getClass().getClassLoader() == null) {
                throw new ParameterException("不支持内置数据类型");
            }
            keys.add(StringUtils.uncapitalize(obj.getClass().getSimpleName()));
            values.add(obj);
        }
        return toJsonString(object, keys.toArray(), values.toArray());
    }

    public static <T> List<Map<String, Object>> toListMap(List<T> objs) {
        if (CollectionUtils.isEmpty(objs)) {
            return null;
        }
        List<Map<String, Object>> result = new ArrayList<>(objs.size());
        for (Object obj : objs) {
            result.add(toMap(obj));
        }
        return result;
    }

    /**
     * 返回原生字符,直接构造字符串进行追加对象，原理上性能要高于toMap方法
     *
     * @param object
     * @param keys
     * @param values
     * @return
     * @throws NSException
     */
    public String toJsonString(Object object, String[] keys, Object[] values) throws NSException {
        if (!ArrayUtils.isSameLength(keys, values)) {
            throw new ParameterException("参数数据量不匹配");
        }
        StringBuilder valBuilder = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            valBuilder.append(CharConstant.DOT).append(StringUtil.dquote(keys[i])).append(CharConstant.COLON).append(GsonUtil.toJson(values[i]));
        }
        valBuilder.append(CharConstant.DOT);
        valBuilder.deleteCharAt(0);
        StringBuilder objBuilder = new StringBuilder(GsonUtil.toJson(object));
        objBuilder.insert(1, valBuilder);
        return objBuilder.toString();
    }
}
