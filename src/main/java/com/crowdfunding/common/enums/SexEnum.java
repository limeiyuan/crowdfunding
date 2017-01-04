package com.crowdfunding.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by LiMeiyuan on 2016/11/10 15:55.
 * description:
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SexEnum {
    female("女", "0"),
    male("男", "1");

    private String name;
    private String value;

    SexEnum(String name, String value) {
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

    public static SexEnum recreateEnum(String value) {
        SexEnum enumVal = null;

        if (value != null) {
            for (SexEnum sex : SexEnum.values()) {
                if (sex.getValue().equalsIgnoreCase(value)) {
                    enumVal = sex;
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
