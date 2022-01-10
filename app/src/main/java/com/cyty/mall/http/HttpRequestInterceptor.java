package com.cyty.mall.http;

import com.blankj.utilcode.util.LogUtils;
import com.cyty.mall.contants.MKParameter;
import com.cyty.mall.util.MkUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/7 9:25
 * @描述
 */
public class HttpRequestInterceptor implements Interceptor {


    @NotNull
    @Override
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        try {
            Request.Builder builder = request.newBuilder();
            // 设置请求头
            String token = MkUtils.decodeString(MKParameter.TOKEN);
            if (token != null) {
                builder.header(HttpConfig.RequestKey.FORM_KEY_TOKEN, "Bearer" + token);
            }
            request = builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //继续发送
        return chain.proceed(request);
    }
}
