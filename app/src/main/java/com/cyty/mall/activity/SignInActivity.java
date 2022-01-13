package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

/**
 * 签到
 */
public class SignInActivity extends BaseActivity {

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_sign_in;
    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, SignInActivity.class);
        mContext.startActivity(mIntent);
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, null);
    }
}