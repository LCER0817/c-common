package com.ns.common.util.exception.sys;

import com.ns.common.util.exception.errorcode.CommonErrorCode;

public class ParameterException extends NSException {
    public ParameterException() {
        super(CommonErrorCode.PARAMETER_EXCEPTION);
    }

    public ParameterException(String msg) {
        super(CommonErrorCode.PARAMETER_EXCEPTION, msg);
    }
}
