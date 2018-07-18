package com.ns.common.util.exception.sys;

import com.ns.common.util.exception.errorcode.CommonErrorCode;

public class SystemInternalException extends NSException {
    public SystemInternalException() {
        super(CommonErrorCode.SYSTEM_INTERNAL_EXCEPTION);
    }
}
