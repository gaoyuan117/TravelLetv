package com.jzbwlkj.travelletv.base;

/**
 * Created by gaoyuan on 2017/3/10.
 */

public class HttpResult<T> {
    public int code;
    public String message;
    public T data;

    public static int SUCCESS = 200;

    public boolean isSuccess() {
        return SUCCESS == code;
    }

}
