package com.ns.common.util.guid;

import java.util.regex.Pattern;

/**
 * Created by xuezhucao on 16/10/21.
 */
public class GuidUtil {
    public static final String PATTERN = "^\\w{8}-(\\w{4}-){3}\\w{12}$";

    public static boolean isValidGuid(String guid) {
        return Pattern.matches(PATTERN, guid);
    }
}
