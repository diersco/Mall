package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;

import java.util.Timer;

/**
 * 物流信息
 */
public class LogisticsInformationActivity extends BaseActivity {


    private int id;
    private String information;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_logistics_information;
    }

    public static void startActivity(Context mContext, int id) {
        Intent mIntent = new Intent(mContext, LogisticsInformationActivity.class);
        mIntent.putExtra(Constant.INTENT_ID, id);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {
        id = getIntent().getIntExtra(Constant.INTENT_ID, 0);
    }

    @Override
    protected void initData() {
        signInRecord();
    }

    @Override
    protected void initToolBar() {

    }

    /**
     * 签到记录
     */
    private void signInRecord() {
        HttpManager.getInstance().queryLogistics(id,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.queryLogisticsResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.queryLogisticsResponse data) {
                        if (result) {
                            information = data.data.replaceAll("\\\\", "");
                            LogUtils.d( "information----" + information);
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }
}