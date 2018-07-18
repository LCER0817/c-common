package com.ns.common.util.exception.sys;


import com.ns.common.util.exception.errorcode.CommonErrorCode;

public class DataBaseOperationException extends NSException {
    public DataBaseOperationException() {
        super(CommonErrorCode.DATABASE_OPERATION_EXCEPTION);
    }
}
