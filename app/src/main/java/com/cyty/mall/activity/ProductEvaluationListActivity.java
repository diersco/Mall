package com.cyty.mall.activity;

import android.view.View;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;

/**
 * 商品评价列表
 */
public class ProductEvaluationListActivity extends BaseActivity {


    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_product_evaluation_list;
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