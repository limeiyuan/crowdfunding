package com.crowdfunding.common.util;

import com.crowdfunding.common.PaginationInfo;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by LiMeiyuan on 2016/9/26 15:37.
 * description:
 */
public class PaginationUtil {
    public static PaginationInfo getPaginationFromRequest() {
        try {
            HttpServletRequest request = WebContextUtil.getRequest();
            String pageNoStr = request.getParameter("pageNo");
            if (StringUtils.isEmpty(pageNoStr)) {
                if (request.getAttribute("pageNo") != null) {
                    pageNoStr = String.valueOf(request.getAttribute("pageNo"));
                }
            }
            if (StringUtils.isNotEmpty(pageNoStr)) {
                PaginationInfo pageInfo = new PaginationInfo();
                String pageSizeStr = request.getParameter("pageSize");
                if (StringUtils.isNotEmpty(pageSizeStr)) {
                    pageSizeStr = String.valueOf(request.getAttribute("pageSize"));
                }
                try {
                    if (StringUtils.isNotEmpty(pageNoStr)) {
                        int pageNo = Integer.parseInt(pageNoStr);
                        pageInfo.setpageNo(pageNo);
                    } else {
                        pageInfo.setpageNo(1);
                    }

                    if (StringUtils.isNotEmpty(pageSizeStr)) {
                        int pageSizes = Integer.parseInt(pageSizeStr);
                        pageInfo.setpageSizes(pageSizes);
                    }

                    return pageInfo;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("不执行分页处理");
            return null;
        }
    }

    public static void initPagination(int pageNo, int pageSize) {
        HttpServletRequest request = WebContextUtil.getRequest();
        request.setAttribute("pageNo", pageNo);
        request.setAttribute("pageSize", pageSize);
    }

    public static void setPaginationToRequest(int count, int pageCount) {
        HttpServletRequest request = WebContextUtil.getRequest();
        if (request.getParameter("pageNo") != null) {
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("count", count);
        }
    }

    public static void setEnable(boolean enable) {
        HttpServletRequest request = WebContextUtil.getRequest();
        if (request != null) {
            request.setAttribute("PAGINATION_ENABLE", enable);
        }

    }

    public static boolean isEnable() {
        HttpServletRequest request = WebContextUtil.getRequest();
        if (request == null) {
            return false;
        }
        Object obj = request.getAttribute("PAGINATION_ENABLE");
        if (obj != null && obj instanceof Boolean) {
            Boolean enable = (Boolean) obj;
            return enable;
        } else {
            return true;
        }
    }
}
