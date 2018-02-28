package com.jd.raiders2.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by houhuang on 18/2/27.
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String url, okhttp3.Callback callback)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
}
