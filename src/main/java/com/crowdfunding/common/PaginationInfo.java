package com.crowdfunding.common;

/**
 * Created by LiMeiyuan on 2016/9/26 15:40.
 * description:
 */
public class PaginationInfo {
    /** 记录总数 **/
    private int count;

    /** 每页行数 **/
    private int pageSizes = 30;

    /** 当前页数 **/
    private int pageNo;

    /** 总页数 **/
    private int pageCount;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getpageSizes() {
        return pageSizes;
    }

    public void setpageSizes(int pageSizes) {
        this.pageSizes = pageSizes;
    }

    public int getpageNo() {
        return pageNo;
    }

    public void setpageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
