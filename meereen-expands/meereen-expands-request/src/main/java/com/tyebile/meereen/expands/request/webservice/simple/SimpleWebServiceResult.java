package com.tyebile.meereen.expands.request.webservice.simple;

import com.tyebile.meereen.expands.request.webservice.WebServiceResult;

public class SimpleWebServiceResult implements WebServiceResult {
    Object data;

    public SimpleWebServiceResult(Object data) {
        this.data = data;
    }

    @Override
    public <T> T get() {
        return (T) data;
    }
}
