package com.tyebile.meereen.expands.request;

import com.tyebile.meereen.expands.request.email.EmailRequest;
import com.tyebile.meereen.expands.request.ftp.FtpRequest;
import com.tyebile.meereen.expands.request.http.HttpRequest;
import com.tyebile.meereen.expands.request.http.HttpRequestGroup;
import com.tyebile.meereen.expands.request.webservice.WebServiceRequestBuilder;
import com.tyebile.meereen.expands.request.websocket.WebSocketRequest;

import java.io.IOException;

public interface RequestBuilder {
    HttpRequestGroup http();

    HttpRequest http(String url);

    HttpRequest https(String url);

    FtpRequest ftp(String host, int port, String username, String password) throws IOException;

    FtpRequest ftp(String host, int port) throws IOException;

    FtpRequest ftp(String host) throws IOException;

    WebServiceRequestBuilder webService() throws Exception;

    EmailRequest email();

    WebSocketRequest webSocket(String url);

}
