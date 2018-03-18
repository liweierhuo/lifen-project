package com.lifen.enums;


import java.util.HashMap;
import java.util.Map;

public enum ProjectTypeEnum {
    OPEN_A_SHOP(0, "创业项目"),
    INNOVATE(1, "创新项目"),
    OTHER_TYPE(2, "其他项目"),
    ;

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ProjectTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<Integer,String> toMap() {
        Map<Integer,String> enumMap = new HashMap<>();
        for (ProjectTypeEnum element: values()) {
            enumMap.put(element.getCode(),element.getMessage());
        }
        return enumMap;
    }


}
