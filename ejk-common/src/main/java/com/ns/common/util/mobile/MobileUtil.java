package com.ns.common.util.mobile;

import com.ns.common.util.constant.MobileConstant;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by xuezhucao on 15/7/28.
 */
public class MobileUtil {
    public static boolean isValidMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        return Pattern.matches(MobileConstant.PATTERN, mobile);
    }

    public static boolean isValidTelePhone(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        return Pattern.matches(MobileConstant.PHONE_PATTERN, mobile);
    }

    public static boolean isValidPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        boolean result = isValidMobile(phone);
        if (!result) {
            result = isValidTelePhone(phone);
        }
        return result;
    }

    public static void main(String[] args) {
        boolean temp = MobileUtil.isValidPhone("010-53152625");
        System.out.println(temp);
    }
}
