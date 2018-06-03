package com.tyebile.meereen.starter.spring.boot.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.tyebile.meereen.utils.StringUtils;
import com.tyebile.meereen.commons.utils.ThreadLocalUtils;
import com.tyebile.meereen.commons.controller.message.ResponseMessage;
import com.tyebile.meereen.core.convert.CustomMessageConverter;
import com.tyebile.meereen.core.dict.DictSupportApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> implements Ordered {

    @Autowired(required = false)
    private DictSupportApi dictSupportApi;

    public final static Charset UTF8 = Charset.forName("UTF-8");

    private Charset charset = UTF8;

    private SerializerFeature[] features = new SerializerFeature[0];

    private List<CustomMessageConverter> converters;

    public FastJsonHttpMessageConverter() {
        super(new MediaType("application", "json", UTF8),
                new MediaType("application", "*+json", UTF8));
    }

    public void setConverters(List<CustomMessageConverter> converters) {
        this.converters = converters;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public SerializerFeature[] getFeatures() {
        return features;
    }

    public void setFeatures(SerializerFeature... features) {
        this.features = features;
    }

    public Object readByString(Class<?> clazz, String jsonStr) {
        return readByBytes(clazz, jsonStr.getBytes());
    }

    public Object readByBytes(Class<?> clazz, byte[] bytes) {
        if (clazz == String.class) {
            return new String(bytes, charset);
        }
        if (null != converters) {
            CustomMessageConverter converter = converters.stream()
                    .filter(cvt -> cvt.support(clazz))
                    .findFirst()
                    .orElse(null);
            if (converter != null) {
                return converter.convert(clazz, bytes);
            }
        }
        Object object = JSON.parseObject(bytes, 0, bytes.length, charset.newDecoder(), clazz);
        if (dictSupportApi != null) {
            object = dictSupportApi.unwrap(object);
        }
        return object;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = inputMessage.getBody();
        byte[] buf = new byte[1024];
        for (; ; ) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }
            if (len > 0) {
                baos.write(buf, 0, len);
            }
        }
        byte[] bytes = baos.toByteArray();
        return readByBytes(clazz, bytes);
    }

    public String converter(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        String text;
        String callback = ThreadLocalUtils.getAndRemove("jsonp-callback");
        if (obj instanceof ResponseMessage) {
            ResponseMessage message = (ResponseMessage) obj;
            if (dictSupportApi != null) {
                message.setResult(dictSupportApi.wrap(message.getResult()));
            }
            text = JSON.toJSONString(obj, parseFilter(message), features);
        } else {
            if (dictSupportApi != null) {
                obj = dictSupportApi.wrap(obj);
            }
            text = JSON.toJSONString(obj, features);
        }
        if (!StringUtils.isNullOrEmpty(callback)) {
            text = new StringBuilder()
                    .append(callback)
                    .append("(").append(text).append(")")
                    .toString();
        }
        return text;
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException {
        OutputStream out = outputMessage.getBody();
        byte[] bytes = converter(obj).getBytes(charset);
        out.write(bytes);
        out.flush();
    }

    public static SerializeFilter[] parseFilter(ResponseMessage<?> responseMessage) {
        List<SerializeFilter> filters = new ArrayList<>();
        if (responseMessage.getIncludes() != null) {
            for (Map.Entry<Class<?>, Set<String>> classSetEntry : responseMessage.getIncludes().entrySet()) {
                SimplePropertyPreFilter filter = new SimplePropertyPreFilter(classSetEntry.getKey());
                filter.getIncludes().addAll(classSetEntry.getValue());
                filters.add(filter);
            }
        }
        if (responseMessage.getExcludes() != null) {
            for (Map.Entry<Class<?>, Set<String>> classSetEntry : responseMessage.getExcludes().entrySet()) {
                SimplePropertyPreFilter filter = new SimplePropertyPreFilter(classSetEntry.getKey());
                filter.getExcludes().addAll(classSetEntry.getValue());
                filters.add(filter);
            }
        }
        PropertyFilter responseMessageFilter = (object, name, value) ->
                !(object instanceof ResponseMessage) || value != null;
        filters.add(responseMessageFilter);

        return filters.toArray(new SerializeFilter[filters.size()]);
    }

}
