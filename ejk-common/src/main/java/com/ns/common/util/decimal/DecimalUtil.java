package com.ns.common.util.decimal;

import com.ns.common.util.exception.sys.ParameterException;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by liqiuwei on 2017/11/21.
 */
public class DecimalUtil {

    // 默认小数运算精度
    private static final int DEF_DIV_SCALE = 2;

    private static final BigDecimal DEF_V = new BigDecimal("0");
    // 默认四舍五入
    private static final int ROUND_MODE = BigDecimal.ROUND_HALF_UP;

    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        return calculate(v1, v2, Operator.ADD);
    }

    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        return calculate(v1, v2, Operator.SUB);
    }

    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        return calculate(v1, v2, Operator.MUL);
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        return calculate(v1, v2, Operator.DIV);
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0 || v2.intValue() == 0) {
            throw new ParameterException();
        }
        return v1.divide(v2, scale, ROUND_MODE);
    }

    /**
     * 等于
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean eq(BigDecimal v1, BigDecimal v2) {
        return compare(v1, v2, Operator.EQ);
    }

    /**
     * 不等于
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean neq(BigDecimal v1, BigDecimal v2) {
        return compare(v1, v2, Operator.NEQ);
    }

    /**
     * 大于
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean gt(BigDecimal v1, BigDecimal v2) {
        return compare(v1, v2, Operator.GT);
    }

    /**
     * 大于等于
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean egt(BigDecimal v1, BigDecimal v2) {
        return compare(v1, v2, Operator.EGT);
    }

    /**
     * 小于
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean lt(BigDecimal v1, BigDecimal v2) {
        return compare(v1, v2, Operator.LT);
    }

    /**
     * 小于等于
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean elt(BigDecimal v1, BigDecimal v2) {
        return compare(v1, v2, Operator.ELT);
    }


    public static boolean isBetween(BigDecimal data, BigDecimal min, BigDecimal max) {
        data = getScaleValue(data);
        min = getScaleValue(min);
        max = getScaleValue(max);
        if (data.compareTo(min) >= 0 && data.compareTo(max) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static BigDecimal getScaleValue(BigDecimal o) {
        BigDecimal n = Objects.isNull(o) ? DEF_V : o;
        return n.setScale(DEF_DIV_SCALE, ROUND_MODE);
    }

    private static BigDecimal calculate(BigDecimal v1, BigDecimal v2, Operator operator) {
        BigDecimal b1 = getScaleValue(v1);
        BigDecimal b2 = getScaleValue(v2);
        switch (operator) {
            case ADD:
                return b1.add(b2);
            case SUB:
                return b1.subtract(b2);
            case MUL:
                return b1.multiply(b2);
            case DIV:
                return div(b1, b2, DEF_DIV_SCALE);
        }
        return null;
    }

    private static boolean compare(BigDecimal v1, BigDecimal v2, Operator operator) {
        BigDecimal b1 = getScaleValue(v1);
        BigDecimal b2 = getScaleValue(v2);
        int result = b1.compareTo(b2);
        switch (operator) {
            case EQ:
                return result == 0;
            case NEQ:
                return result != 0;
            case GT:
                return result == 1;
            case EGT:
                return result >= 0;
            case LT:
                return result == -1;
            case ELT:
                return result <= 0;
        }
        return false;
    }


    enum Operator {
        ADD, SUB, MUL, DIV, EQ, NEQ, GT, EGT, LT, ELT;
    }

}
