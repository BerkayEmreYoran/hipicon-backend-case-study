package com.hipicon.casestudy.base;

import java.io.Serializable;

public class Response<D> implements Serializable {

    public D data;
    public Object error;
    public Object metaData;

    public Response() {
    }

    public Response(D data) {
        this.data = data;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object getMetaData() {
        return metaData;
    }

    public void setMetaData(Object metaData) {
        this.metaData = metaData;
    }
}