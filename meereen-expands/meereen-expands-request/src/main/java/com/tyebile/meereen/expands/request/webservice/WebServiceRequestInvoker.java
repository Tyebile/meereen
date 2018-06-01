package com.tyebile.meereen.expands.request.webservice;

public interface WebServiceRequestInvoker {
    WebServiceResult invoke(Object... param) throws Exception;
}
