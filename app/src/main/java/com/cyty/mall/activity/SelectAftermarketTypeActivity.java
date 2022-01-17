package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择售后类型
 */
public class SelectAftermarketTypeActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_goods_title)
    TextView tvGoodsTitle;
    @BindView(R.id.tv_format)
    TextView tvFormat;
    @BindView(R.id.layout_goods)
    LinearLayout layoutGoods;
    @BindView(R.id.tv_mine_about_us)
    TextView tvMineAboutUs;
    @BindView(R.id.tv_return_the_goods)
    TextView tvReturnTheGoods;
    @BindView(R.id.tv_refund_and_goods)
    TextView tvRefundAndGoods;
    @BindView(R.id.tv_after_sales)
    TextView tvAfterSales;


    @Override
    protected void onNetReload(View v) {

    }
    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvTitle.setText("选择售后类型");
    }
    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, SelectAftermarketTypeActivity.class);
        mContext.startActivity(mIntent);
    }
    @Override
    protected int bindLayout() {
        return R.layout.activity_select_aftermarket_type;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }




    @OnClick({R.id.layout_goods, R.id.tv_mine_about_us, R.id.tv_return_the_goods, R.id.tv_refund_and_goods, R.id.tv_after_sales})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_goods:
                break;
            case R.id.tv_mine_about_us:
                break;
            case R.id.tv_return_the_goods:
                break;
            case R.id.tv_refund_and_goods:
                break;
            case R.id.tv_after_sales:
                break;
        }
    }


    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }
}