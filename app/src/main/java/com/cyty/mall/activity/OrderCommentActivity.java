package com.cyty.mall.activity;

import android.view.View;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;

/**
 * 订单评价
 */
public class OrderCommentActivity extends BaseActivity {



    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_order_comment;
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