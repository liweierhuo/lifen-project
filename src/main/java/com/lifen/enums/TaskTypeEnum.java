package com.lifen.enums;


import java.util.HashMap;
import java.util.Map;

public enum TaskTypeEnum {
    OPEN_TITLE_TASK(0, "开题任务"),
    NORMAL_TASK(1, "正式任务"),
    OTHER_TASK(2, "其他"),
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

    TaskTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<Integer,String> toMap() {
        Map<Integer,String> enumMap = new HashMap<>();
        for (TaskTypeEnum element: values()) {
            enumMap.put(element.getCode(),element.getMessage());
        }
        return enumMap;
    }


}
