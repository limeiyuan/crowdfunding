package com.crowdfunding.common.auth;

import com.crowdfunding.common.dto.CustomServiceDto;
import com.crowdfunding.common.util.WebContextUtil;
import com.crowdfunding.domain.Account;

import javax.servlet.http.HttpSession;

/**
 * Created by LiMeiyuan on 2016/11/9 15:21.
 * description:
 */
public class AuthHelper {
    private static final String LOGIN_USER = "loginUser";
    private static final String CUSTOM_SERVICE_DTO = "customServiceDto";

    public static void login(Account account) {
        HttpSession session = WebContextUtil.getSession();

        CustomServiceDto customServiceDto = new CustomServiceDto();
        customServiceDto.setUserId(account.getId() + "");
        customServiceDto.setName(account.getScreenname());
        customServiceDto.setEmail(account.getEmail());
        customServiceDto.setTel(account.getUsername());

        session.setAttribute(LOGIN_USER, account);
        session.setAttribute(CUSTOM_SERVICE_DTO, customServiceDto);
    }

    public static void setCustomServiceDto(CustomServiceDto dto) {
        HttpSession session = WebContextUtil.getSession();
        session.setAttribute(CUSTOM_SERVICE_DTO, dto);
    }

    public static CustomServiceDto getCustomServiceDto() {
        HttpSession session = WebContextUtil.getSession();
        if (session == null) return null;
        return (CustomServiceDto) session.getAttribute(CUSTOM_SERVICE_DTO);
    }

    public static Account getLoginUser() {
        HttpSession session = WebContextUtil.getSession();
        if (session == null) return null;
        return (Account) session.getAttribute(LOGIN_USER);
    }

    public static void logout() {
        HttpSession session = WebContextUtil.getSession();
        if (session != null) {
            session.removeAttribute(LOGIN_USER);
            session.removeAttribute(CUSTOM_SERVICE_DTO);
            session.invalidate();
        }
    }
}
