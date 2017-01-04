package com.crowdfunding.common.util;

import com.crowdfunding.common.WebApplicationLisetener;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by LiMeiyuan on 2016/9/26 15:38.
 * description:
 */
public class WebContextUtil {
    public static HttpServletRequest getRequest() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return request;
        } catch (Exception e) {
            return null;
        }

    }

    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        return request.getSession(true);
    }

    public static String getServletContextName() {
        return getRequest().getContextPath();
    }

    public static String getFullUrlPath(){
        String fullPath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+getServletContextName();
        return fullPath;
    }

    public static String getContextRealPath() {
        ServletContext sc = WebApplicationLisetener.getServletContext();
        return sc.getRealPath("/");
    }
}
