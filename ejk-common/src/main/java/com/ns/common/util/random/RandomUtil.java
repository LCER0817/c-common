package com.ns.common.util.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xuezhucao on 15/7/27.
 */
public class RandomUtil {
    protected static Random random = new Random();

    public static String getNum(int digit) {
        List<Integer> digits = new ArrayList<Integer>(digit);
        StringBuilder builder = new StringBuilder(digit);
        int d;
        while (builder.length() < digit) {
            d = random.nextInt(10);
            if (!digits.contains(d)) {
                digits.add(d);
                builder.append(d);
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(RandomUtil.getNum(4));
    }
}
