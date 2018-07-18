package com.ns.common.util.bean;

import com.ns.common.util.exception.errorcode.CommonErrorCode;
import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.gson.GsonUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by xuezhucao on 16/11/24.
 */
public abstract class BeanUtil {
    public static boolean containsInteger(Integer[] arr, Integer obj) {
        return ArrayUtils.contains(arr, obj);
    }

    public static boolean containsLong(Long[] arr, Long obj) {
        return ArrayUtils.contains(arr, obj);
    }

    public static boolean containsString(String[] arr, String obj) {
        return ArrayUtils.contains(arr, obj);
    }


    public static <T> boolean isFiledsNull(T obj) throws NSException {
        Stream<Field> fieldStream = Stream.of(obj.getClass().getDeclaredFields());
        List<String> names = fieldStream.map(field -> field.getName()).collect(Collectors.toList());
        return isFiledsNull(obj, names.toArray(new String[]{}));
    }

    /**
     * 利用反射判断对象的所有的属性是否为空
     *
     * @param obj
     * @param fieldNames
     * @param <T>
     * @return
     */
    public static <T> boolean isFiledsNull(T obj, String... fieldNames) throws NSException {
        try {
            Object field;
            for (String fieldName : fieldNames) {
                field = getFieldValueByName(obj, fieldName);
                if (field instanceof String) {
                    if (StringUtils.isNotEmpty(field.toString())) {
                        return false;
                    }
                } else if (!Objects.isNull(field)) {
                    return false;
                }
            }
        } catch (Exception e) {
            throw new NSException(CommonErrorCode.OPERATIOIN_FAIL);
        }
        return true;
    }

    public static <T> Object getFieldValueByName(T obj, String fieldName) throws Exception {
        Class clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }


    public static <T> T transMap2Bean(Object object, Class<T> classOfT) {
        if (object == null || !(object instanceof Map)) {
            return null;
        }
        String tmp = GsonUtil.toJson(object);
        return GsonUtil.fromJson(tmp, classOfT);
    }

}
