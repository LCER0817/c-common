package com.ns.common.util.http;

import com.ns.common.util.constant.HttpClientConstant;
import org.apache.commons.httpclient.methods.PostMethod;

public class UTF8PostMethod extends PostMethod {

    public UTF8PostMethod(String url) {
        super(url);
    }

    @Override
    public String getRequestCharSet() {
        return HttpClientConstant.CHARSET;
    }

}
