package com.ns.common.util.fl;

import java.math.BigDecimal;

/**
 * Created by xuezhucao on 15/9/2.
 */
public class FloatUtil {
    public static float getRoundFloor(float o, int scale) {
        BigDecimal b = new BigDecimal(o);
        o = b.setScale(scale, BigDecimal.ROUND_FLOOR).floatValue();
        return o;
    }
}
