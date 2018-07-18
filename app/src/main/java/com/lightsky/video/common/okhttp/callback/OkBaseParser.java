package com.lightsky.video.common.okhttp.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Ivan.L on 2018/7/4.
 * 负责对返回值进行解析，使用策略设计模式
 */

public abstract class OkBaseParser<T> {
    protected int code;

    protected abstract T parse(Response response) throws IOException;

    public T parseResponse(Response response) throws IOException {
        code = response.code();
        return parse(response);
    }

    public int getCode() {
        return code;
    }
}
