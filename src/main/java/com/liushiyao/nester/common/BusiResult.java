package com.liushiyao.nester.common;

import com.liushiyao.nester.utils.JsonUtil;

/**
 * @param <T>
 */
public class BusiResult<T> {
    private int code;
    private String message;
    private T data;

    public BusiResult(BusiStatus status){
        this(status,null);
    }

    public BusiResult(BusiStatus status,T data){
        this.code = status.value();
        this.message = status.getReasonPhrase();
        this.data = data;
    }

    public String toJson(){
        return JsonUtil.toJson(this);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
