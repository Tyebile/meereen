package com.tyebile.meereen.expands.request.http.simple;


import org.apache.commons.codec.digest.DigestUtils;
import com.tyebile.meereen.expands.request.RequestBuilder;
import com.tyebile.meereen.expands.request.SimpleRequestBuilder;
import com.tyebile.meereen.expands.request.http.HttpRequest;
import com.tyebile.meereen.expands.request.http.HttpRequestGroup;
import com.tyebile.meereen.expands.request.http.Response;
import com.tyebile.meereen.expands.request.webservice.WebServiceRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouhao on 16-6-23.
 */
public class SimpleRequestBuilderTest {

    RequestBuilder builder;

    @Before
    public void setup() {
        builder = new SimpleRequestBuilder();
    }


    @Test
    public void testHttpDownload() throws Exception {
        long t = System.currentTimeMillis();
        String json = builder.http("http://localhost:8088/file/upload-static")
                .upload("file", new FileInputStream("/home/zhouhao/桌面/logo.jpg"), "test.jpg").asString();
        System.out.println(json);
        System.out.println(System.currentTimeMillis() - t);
    }

    @Test
    public void testHttp() throws IOException {
        HttpRequestGroup group = builder.http();

        String login = group.request("http://demo.hsweb.me/login")
                .param("username", "test")
                .param("password", "123456").post().asString();

        System.out.println(login);
        System.out.println(group.request("http://demo.hsweb.me/online").get().asString());

        System.out.println(group.request("http://demo.hsweb.me/user").get().asString());
    }

    @Test
    public void testHttps() throws IOException {
        HttpRequest request = builder.https("https://www.baidu.com/");
        Response response = request.get();
        System.out.println(response.asString());
    }

    @Test
    public void testBihuHttps() throws IOException{

    }


    @Test
    public void testFtp() throws IOException {
        builder.ftp("192.168.2.142", 2121, "", "")
                .encode("gbk")
                .ls()
                .forEach(System.out::println);
    }

    @Test
    public void testEmail() throws Exception {
        // TODO: 16-9-29
        builder.email()
                .setting("host", "smtp.qq.com")
                .setting("username", "")
                .setting("password", "")
                .connect()
                .createMessage()
                .to("admin@hsweb.me")
                .content("test..", "text/html")
                .send();
    }

    @Test
    public void testWebService() throws Exception {
        WebServiceRequest request = builder.webService()
                .wsdl("/home/zhouhao/云文档/项目/apsp/接口文档/WSDL/查询密码验证.wsdl");
        System.out.println(request.interfaces());
        System.out.println(request.services());
        for (String s : request.interfaces()) {
            Method[] methods = request.methods(s);
            for (Method method : methods) {
                System.out.println(method);
            }
        }
    }

}