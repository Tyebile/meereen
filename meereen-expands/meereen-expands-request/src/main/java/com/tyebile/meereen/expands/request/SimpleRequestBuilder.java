package com.tyebile.meereen.expands.request;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import com.tyebile.meereen.expands.request.email.EmailRequest;
import com.tyebile.meereen.expands.request.ftp.FtpRequest;
import com.tyebile.meereen.expands.request.ftp.simple.SimpleFtpRequest;
import com.tyebile.meereen.expands.request.http.HttpRequest;
import com.tyebile.meereen.expands.request.http.HttpRequestGroup;
import com.tyebile.meereen.expands.request.http.simple.SimpleHttpRequest;
import com.tyebile.meereen.expands.request.http.simple.SimpleHttpsRequest;
import com.tyebile.meereen.expands.request.http.simple.SimpleRequestGroup;
import com.tyebile.meereen.expands.request.webservice.SimpleWebServiceRequestBuilder;
import com.tyebile.meereen.expands.request.webservice.WebServiceRequestBuilder;
import com.tyebile.meereen.expands.request.websocket.WebSocketRequest;

import java.io.IOException;

public class SimpleRequestBuilder implements RequestBuilder {

    private PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();

    @Override
    public HttpRequestGroup http() {
        return new SimpleRequestGroup();
    }

    @Override
    public HttpRequest http(String url) {
        if (!url.startsWith("http")) url = "http://" + url;
        SimpleHttpRequest request = new SimpleHttpRequest(url);
        request.setPool(pool);
        return request;
    }

    public HttpRequest https(String url) {
        if (!url.startsWith("http")) url = "https://" + url;
        try {
            return new SimpleHttpsRequest(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FtpRequest ftp(String host, int port, String username, String password) throws IOException {
        return new SimpleFtpRequest(host, port, username, password);
    }

    @Override
    public FtpRequest ftp(String host, int port) throws IOException {
        return ftp(host, port, null, null);
    }

    @Override
    public FtpRequest ftp(String host) throws IOException {
        return ftp(host, 22);
    }

    @Override
    public WebServiceRequestBuilder webService() throws Exception {
        return new SimpleWebServiceRequestBuilder();
    }


    @Override
    public EmailRequest email() {
        // TODO: 16-9-29
        throw new UnsupportedOperationException();
    }

    @Override
    public WebSocketRequest webSocket(String url) {
        // TODO: 16-9-29
        throw new UnsupportedOperationException();
    }

}
