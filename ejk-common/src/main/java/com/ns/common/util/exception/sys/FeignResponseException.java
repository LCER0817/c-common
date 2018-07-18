package com.ns.common.util.exception.sys;

import com.ns.common.util.exception.errorcode.CommonErrorCode;

public class FeignResponseException extends NSException {

    public FeignResponseException(String appName) {
        super(CommonErrorCode.FEIGN_RESPONSE_FAIL, appName);
    }
}
