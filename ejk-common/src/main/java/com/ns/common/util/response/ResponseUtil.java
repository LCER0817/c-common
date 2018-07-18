package com.ns.common.util.response;

import com.ns.common.bean.Head;
import com.ns.common.bean.Response;
import com.ns.common.util.exception.errorcode.ErrorCode;
import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.exception.sys.SystemInternalException;

public class ResponseUtil {

    private static final Integer SUCCESS_CODE = 0;

    public static <T> T getResultData(Response<T> response) throws NSException {
        checkHead(response);
        T data = response.getData();
        return data;
    }

    public static <T> boolean isSuccess(Response<T> response) {
        if (response == null) {
            throw new SystemInternalException();
        }
        Head head = response.getHead();
        if (head == null) {
            throw new SystemInternalException();
        } else if (!SUCCESS_CODE.equals(head.getErrCode())) {
            return false;
        }
        return true;
    }

    private static <T> void checkHead(Response<T> response) throws NSException {
        if (response == null) {
            throw new SystemInternalException();
        }
        Head head = response.getHead();
        if (head == null) {
            throw new SystemInternalException();
        } else if (!SUCCESS_CODE.equals(head.getErrCode())) {
            throw new NSException(new ErrorCode(head.getErrCode(), head.getErrMsg()));
        }
    }

}
