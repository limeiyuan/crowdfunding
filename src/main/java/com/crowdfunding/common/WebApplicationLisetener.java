package com.crowdfunding.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by LiMeiyuan on 2016/9/26 15:38.
 * description:
 */
public class WebApplicationLisetener {
    private static WebApplicationContext wac;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(applicationContext instanceof WebApplicationContext){
            wac = (WebApplicationContext)applicationContext;
        }
    }

    public static WebApplicationContext getWac(){
        return wac;
    }

    public static String getServletContextName(){
        return wac.getServletContext().getServletContextName();
    }

    public static ServletContext getServletContext(){
        return wac.getServletContext();
    }
}
