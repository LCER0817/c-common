package com.ns.common.util.checker;

import com.ns.common.bean.Id;
import com.ns.common.util.bean.BeanUtil;
import com.ns.common.util.decimal.DecimalUtil;
import com.ns.common.util.exception.errorcode.ErrorCode;
import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.exception.sys.ParameterException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by xuezhucao on 16/11/24.
 */
public abstract class BeanChecker {
    public static void assertNotNull(Object obj, String errMsg) throws NSException {
        if (obj == null) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertNotEmpty(String str, String errMsg) throws NSException {
        if (StringUtils.isEmpty(str)) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertNotEmpty(Collection collection, String errMsg) throws NSException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertNotEmpty(Map map, String errMsg) throws NSException {
        if (MapUtils.isEmpty(map)) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertPositive(Long o, String errMsg) throws NSException {
        if (o == null || o <= 0) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertPositive(Integer o, String errMsg) throws NSException {
        if (o == null || o <= 0) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertPositive(Float o, String errMsg) throws NSException {
        if (o == null || o <= 0) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertPositive(BigDecimal o, String errMsg) throws NSException {
        if (o == null || o.compareTo(new BigDecimal("0")) <= 0) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertLongPositive(List<Long> objs, String errMsg) throws NSException {
        for (Long o : objs) {
            assertPositive(o, errMsg);
        }
    }

    public static void assertIntegerPositive(List<Integer> objs, String errMsg) throws NSException {
        for (Integer o : objs) {
            assertPositive(o, errMsg);
        }
    }

    public static void assertNotNegative(Long o, String errMsg) throws NSException {
        if (o == null || o < 0) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertNotNegative(Integer o, String errMsg) throws NSException {
        if (o == null || o < 0) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertNotNegative(BigDecimal o, String errMsg) throws NSException {
        if (o == null || o.compareTo(new BigDecimal("0")) < 0) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertNotNegativeLong(List<Long> objs, String errMsg) throws NSException {
        for (Long o : objs) {
            assertNotNegative(o, errMsg);
        }
    }

    public static void assertNotNegativeInteger(List<Integer> objs, String errMsg) throws NSException {
        for (Integer o : objs) {
            assertNotNegative(o, errMsg);
        }
    }

    public static void assertPattern(String str, String pattern, String errMsg) throws NSException {
        if (StringUtils.isEmpty(str)
                || StringUtils.isEmpty(pattern)
                || !Pattern.matches(pattern, str)) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertPattern(String str, String pattern, ErrorCode errCode) throws NSException {
        if (StringUtils.isEmpty(str)
                || StringUtils.isEmpty(pattern)
                || !Pattern.matches(pattern, str)) {
            throw new NSException(errCode);
        }
    }

    public static void assertBefore(Date date1, Date date2, String errMsg) throws NSException {
        if (date1 == null || date2 == null
                || !date1.before(date2)) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertContains(Integer[] arr, Integer obj, String errMsg) throws NSException {
        if (!BeanUtil.containsInteger(arr, obj)) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertContains(Long[] arr, Long obj, String errMsg) throws NSException {
        if (!BeanUtil.containsLong(arr, obj)) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertContains(String[] arr, String obj, String errMsg) throws NSException {
        if (!BeanUtil.containsString(arr, obj)) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertBetween(BigDecimal data, BigDecimal min, BigDecimal max, String errMsg) throws NSException {
        if (!DecimalUtil.isBetween(data, min, max)) {
            throw new ParameterException(errMsg);
        }
    }

    public static void assertMaxLength(String str, int maxLength, String errMsg) {
        if (StringUtils.isNotEmpty(str)
                && str.length() > maxLength) {
            throw new ParameterException(errMsg);
        }
    }

    public void checkId(Id id) throws NSException {
        assertNotNull(id, "id对象为空");
        assertNotNull(id.getId(), "id为空");
    }
}
