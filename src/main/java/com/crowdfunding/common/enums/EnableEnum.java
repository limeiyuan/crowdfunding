package com.crowdfunding.common.enums;


import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by LiMeiyuan on 2016/11/10 15:33.
 * description:
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnableEnum {
    disable("禁用", "0"),
    enable("启用", "1");

    private String name;
    private String value;

    EnableEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static EnableEnum recreateEnum(String value) {
        EnableEnum enumVal = null;

        if (value != null) {
            for (EnableEnum enable : EnableEnum.values()) {
                if (enable.getValue().equalsIgnoreCase(value)) {
                    enumVal = enable;
                    break;
                }
            }
        }
        return enumVal;
    }

    public String recreateString() {
        return value;
    }
}
