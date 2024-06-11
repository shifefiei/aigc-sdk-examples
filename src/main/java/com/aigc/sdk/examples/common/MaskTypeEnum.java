package com.aigc.sdk.examples.common;

public enum MaskTypeEnum {

    RECT("rect", "矩形"),

    CIRCLE("circle", "圆形"),

    NO_MASK("noMask", "保持原状");


    private String code;
    private String msg;


    MaskTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
