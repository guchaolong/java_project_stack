package com.zeki.globalexceptionhandle.exception;

/**
 * Description:
 *
 * @author GUCHAOLONG
 * @date 2021/4/9 5:55
 */
public class ParamException extends RuntimeException {

    private String code;

    public ParamException(String msg){
        super(msg);
    }

    public ParamException(String msg, String code) {
        super(msg);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
