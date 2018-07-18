package com.ns.common.util.exception.sys;

import com.ns.common.util.exception.errorcode.CommonErrorCode;

public class CompoentStatusException extends NSException {

    public CompoentStatusException() {
        super(CommonErrorCode.COMPOENT_STATUS_ERROR);
    }

    public CompoentStatusException(String compoent, String status) {
        super(CommonErrorCode.COMPOENT_STATUS_ERROR, compoent, status);
    }
}
;