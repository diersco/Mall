package com.cyty.mall.activity;

import android.view.View;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;

/**
 * 新增地址
 */
public class AddAddressActivity extends BaseActivity {


    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView() {

    }
    @Override
    protected void initToolBar() {
        // 设置toolbar
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//        }
//        tvTitle.setText("个人设置");
    }
    @Override
    protected void initData() {

    }
}