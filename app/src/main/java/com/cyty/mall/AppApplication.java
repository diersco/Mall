package com.cyty.mall;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.chad.library.BuildConfig;
import com.cyty.mall.callback.CustomCallback;
import com.cyty.mall.callback.EmptyCallback;
import com.cyty.mall.callback.ErrorCallback;
import com.cyty.mall.callback.LoadingCallback;
import com.cyty.mall.callback.TimeoutCallback;
import com.cyty.mall.util.MkUtils;
import com.hjq.toast.ToastUtils;
import com.kingja.loadsir.core.LoadSir;
import com.tencent.mmkv.MMKV;

import timber.log.Timber;
import timber.log.Timber.DebugTree;
/**
 * @创建者 misJackLee
 * @创建时间 2021/12/9 15:59
 * @描述
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
    /**
     * 初始化三方库
     */
    private void init() {

        //字段存储
        MMKV.initialize(this);
        //初始化
        MkUtils.getInstance();
        //加载反馈框架
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();

        ToastUtils.init(this);
    }
}

