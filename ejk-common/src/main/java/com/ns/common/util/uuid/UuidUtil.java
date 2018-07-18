package com.ns.common.util.uuid;

import java.util.Random;
import java.util.UUID;

/**
 * Created by xuezhucao on 15/7/21.
 */
public class UuidUtil {
    private static Random random = new Random();

    public static String getUuid() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String result = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
        return result;
    }

    public static String getUuid(int length) {
        String result = getUuid();
        if (length >= result.length()) {
            return result;
        } else {
            int maxStart = result.length() - length;
            int start = random.nextInt(maxStart);
            return result.substring(start, start + length);
        }
    }

    public static void main(String[] args) {
        System.out.println(UuidUtil.getUuid(10));
    }
}
