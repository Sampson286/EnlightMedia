package com.enlight.android.common.retrofitnetwork.modle;

/**
 * Created by zyc on 2017/8/30.
 * 基本的数据类型用于判断响应信息
 */

public class BaseData<T> {
    //状态码
    private int status;
    //状态信息
    private String msg;
    //错误信息
    private String error;
    //具体的数据
    private T data;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
