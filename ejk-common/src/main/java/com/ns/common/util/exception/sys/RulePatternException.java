package com.ns.common.util.exception.sys;

import com.ns.common.util.exception.errorcode.CommonErrorCode;

public class RulePatternException extends NSException {
    public RulePatternException() {
        super(CommonErrorCode.PARAMETER_EXCEPTION);
    }

    /**
     * @param msg fileName,errorText,rightPattern
     */
    public RulePatternException(String... msg) {
        super(CommonErrorCode.ROLE_PATTERN_EXCEPTION, msg);
    }
}
