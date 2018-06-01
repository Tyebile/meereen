package com.tyebile.meereen.expands.request.http;

/**
 * @author zhouhao
 */
public interface HttpRequestGroup {

    String getCookie();

    HttpRequestGroup clearCookie();

    HttpRequest request(String url);

}
