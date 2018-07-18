package com.ns.common.bean;

import com.ns.common.util.constant.PageConstant;

/**
 * 分页对象
 * Created by liqiuwei on 2017/8/4.
 */
public class EjkPage {

    public EjkPage() {

        this.offset = PageConstant.DEFAULT_OFFSET;
        this.pagesize = PageConstant.DEFAULT_PAGE_SIZE;
    }

    /**
     * 偏移数
     */
    private int offset;
    /**
     * 每页显示多少条记录
     */
    private int pagesize;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
