package com.lifen.enums;

public enum IsDeletedEnum {
    YES("y", "已删除"),
    NO("n", "未删除"),
    ;
    private String code;

    private String message;

    IsDeletedEnum(String code, String message) {
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
}
