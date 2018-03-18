package com.lifen.enums;


public enum ProjectStatusEnum {
    DRAFT("draft", "草稿"),
    NORMAL("normal", "正常"),
    OVER("over", "结束"),
    ;

    private String code;

    private String message;

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

    ProjectStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
