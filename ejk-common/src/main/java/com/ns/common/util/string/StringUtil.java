package com.ns.common.util.string;

import com.ns.common.util.constant.CharConstant;

public class StringUtil {
    public static String getStrOfLength(char prefix, String str, int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(prefix);
        }
        builder.append(str);
        String result = builder.substring(builder.length() - length);
        return result;
    }

    /**
     * Returns a double quoted string.
     *
     * @param str a String
     * @return {@code "str"}
     */
    public static String dquote(final String str) {
        return CharConstant.DQUOTE + str + CharConstant.DQUOTE;
    }

    /**
     * Returns a double doted string.
     *
     * @param str a String
     * @return {@code "str"}
     */
    public static String ddot(final String str) {
        return CharConstant.DOT + str + CharConstant.DOT;
    }

    public static void main(String[] args) {
        System.out.println((long) Math.pow(10, 12));
        System.out.println(StringUtil.getStrOfLength('0', "123", 12));
    }
}
