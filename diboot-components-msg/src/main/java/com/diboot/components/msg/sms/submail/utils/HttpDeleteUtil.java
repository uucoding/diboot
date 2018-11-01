package com.diboot.components.msg.sms.submail.utils;

/**
 * 继承自HttpEntityEnclosingRequestBase，覆盖其中的getMethod方法，使其返回“DELETE”，
 * 使HttpDelete可以发送body信息。
 */
import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpDeleteUtil extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";

    @Override
    public String getMethod() { return METHOD_NAME; }

    public HttpDeleteUtil(final String uri) {
        super();
        setURI(URI.create(uri));
    }
    public HttpDeleteUtil(final URI uri) {
        super();
        setURI(uri);
    }
    public HttpDeleteUtil() { super(); }
}
