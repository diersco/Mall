package com.cyty.mall.http;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/4 13:57
 * @描述
 */
public class HttpEngine {

    private ConcurrentHashMap<String, Call> map = new ConcurrentHashMap<>();
    private OkHttpClient okHttpClient;

    private static final int NET_CONNECT_TIME_OUT = 10;
    private static final int NET_READ_TIME_OUT = 10;
    private static final int NET_WRITE_TIME_OUT = 10;

    public HttpEngine() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(NET_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(NET_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(NET_WRITE_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpRequestInterceptor())
                .addInterceptor(logging)
                .build();

    }

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Timber.d(message);
        }
    });

    public <T extends HttpResponse> void request(Request request, final Class<T> responseClass,
                                                 final HttpResponseResultCallback<T> callback) {
        Call call = okHttpClient.newCall(request);

        map.put(request.url().toString(), call);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                callbackPost(new Runnable() {

                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onResponse(false, "request failure, error message : " + e.getMessage(), null);
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                String requestUrl = request.url().encodedPath();
                LogUtils.d(requestUrl + "----" + result);
                Gson gson = new Gson();
                try {
                    final T resp = gson.fromJson(result, responseClass);
                    if (resp == null) {
                        return;
                    }

                    String message = resp.msg;

                    boolean isTrue = false;
                    if (resp.code == 200) isTrue = true;


                    if (callback != null) {
                        final String finalErrorMessage = message;
                        boolean finalIsTrue = isTrue;
                        callbackPost(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(finalIsTrue, finalErrorMessage, resp);
                            }
                        });
                    }
                } catch (Exception e) {
                    onFailure(call, new IOException(e.getMessage()));
                }

            }
        });
    }

    public <T extends HttpResponse> void request(Request request, final Class<T> responseClass,
                                                 final HttpResponseResultListCallback<T> callback) {
        Call call = okHttpClient.newCall(request);

        map.put(request.url().toString(), call);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                callbackPost(new Runnable() {

                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onResponse(false, 0, "request failure, error message : " + e.getMessage(), null);
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                String requestUrl = request.url().encodedPath();
                LogUtils.d(requestUrl + "----" + result);
                Gson gson = new Gson();

                try {
                    final T resp = gson.fromJson(result, responseClass);
                    if (resp == null) {
                        return;
                    }

                    String message = resp.msg;
                    int totalNum = resp.total;
                    boolean isTrue = false;
                    if (resp.code == 200) isTrue = true;


                    if (callback != null) {
                        final String finalErrorMessage = message;
                        boolean finalIsTrue = isTrue;
                        callbackPost(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(finalIsTrue, totalNum, finalErrorMessage, resp);
                            }
                        });
                    }
                } catch (Exception e) {
                    onFailure(call, new IOException(e.getMessage()));
                }

            }
        });
    }

    private void callbackPost(Runnable runnable) {
        ThreadUtils.runOnUiThread(runnable);
    }

    public void cancel(String url) {
        if (map != null && map.containsKey(url)) {
            Call remove = map.remove(url);
            remove.cancel();
        }
    }

    public interface HttpResponseResultCallback<T> {
        void onResponse(boolean result, String message, T data);
    }

    public interface HttpResponseResultListCallback<T> {
        void onResponse(boolean result, int total, String message, T data);
    }
}
