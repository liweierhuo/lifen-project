package com.lifen.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum UserTypeEnum {
    TEACHER("1", "教师"),
    STUDENT("2", "学生"),
    ;
    private String code;

    private String message;

    UserTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Map<String,String> toMap(){
        Map<String, String> map = new HashMap<String, String>();
        for(UserTypeEnum element: values()) {
            map.put(element.getCode(),element.getMessage());
        }
        return map;
    }
}
