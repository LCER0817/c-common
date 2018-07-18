package com.ns.common.bean;

import java.io.Serializable;

public class Response<T> implements Serializable {

    private Head head;

    private T data;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse [head=" + head + ", data=" + data + "]";
    }


}
