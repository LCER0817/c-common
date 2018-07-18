package com.ns.common.bean;

import java.util.List;

public class EjkObjPage<T> {

    private int totalCount;

    private List<T> resultList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
