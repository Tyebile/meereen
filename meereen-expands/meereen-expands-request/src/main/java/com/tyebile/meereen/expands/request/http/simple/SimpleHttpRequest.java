package com.tyebile.meereen.expands.request.http.simple;

import org.apache.http.HttpResponse;
import com.tyebile.meereen.expands.request.http.Response;

import java.io.IOException;

/**
 * Created by zhouhao on 16-6-28.
 */
public class SimpleHttpRequest extends AbstractHttpRequest {
    public SimpleHttpRequest(String url) {
        super(url);
    }

    @Override
    protected Response getResultValue(HttpResponse res) throws IOException {
        return new SimpleResponse(res);
    }
}
