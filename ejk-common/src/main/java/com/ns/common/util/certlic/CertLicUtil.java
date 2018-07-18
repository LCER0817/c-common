package com.ns.common.util.certlic;

import com.ns.common.util.constant.CertLicConstant;
import org.apache.commons.lang.StringUtils;

/**
 * Created by xuezhucao on 16/8/17.
 */
public class CertLicUtil {
    public static boolean isValidCertLicNum(String certLicNum) {
        if (StringUtils.isEmpty(certLicNum)) {
            return false;
        }
        return certLicNum.matches(CertLicConstant.PATTERN);
    }

    public static void main(String[] args) {
        System.out.println(isValidCertLicNum("12010319820726481x"));
    }
}
