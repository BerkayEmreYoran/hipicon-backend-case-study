package com.hipicon.casestudy.base;

public abstract class BaseControllerV1 {

    public <T> Response<T> ok(T data) {
        return new Response<>(data);
    }
}