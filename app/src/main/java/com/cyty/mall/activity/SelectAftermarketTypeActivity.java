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
import com.cyty.mall.bean.AddressInfo;
import com.cyty.mall.bean.OrderDetailInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.util.GlideUtil;
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
    @BindView(R.id.tv_return_the_goods)
    TextView tvReturnTheGoods;
    @BindView(R.id.tv_refund_and_goods)
    TextView tvRefundAndGoods;
    @BindView(R.id.tv_after_sales)
    TextView tvAfterSales;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_num)
    TextView tvNum;
    private String orderID;
    private OrderDetailInfo.OrderDetailsListBean orderDetailsListBean;
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


    public static void startActivity(Context mContext, OrderDetailInfo.OrderDetailsListBean orderDetailsListBean, String orderID) {
        Intent mIntent = new Intent(mContext, SelectAftermarketTypeActivity.class);
        mIntent.putExtra(Constant.INTENT_DATA, orderDetailsListBean);
        mIntent.putExtra(Constant.INTENT_ID, orderID);
        mContext.startActivity(mIntent);
    }
    @Override
    protected int bindLayout() {
        return R.layout.activity_select_aftermarket_type;
    }

    @Override
    protected void initView() {
        orderDetailsListBean = (OrderDetailInfo.OrderDetailsListBean) getIntent().getParcelableExtra(Constant.INTENT_DATA);
        orderID = getIntent().getStringExtra(Constant.INTENT_ID);
    }

    @Override
    protected void initData() {
        GlideUtil.with(mContext).displayImage(orderDetailsListBean.getThumbnail(), ivCover);
        tvGoodsTitle.setText(orderDetailsListBean.getGoodsTitle());
        tvFormat.setText(orderDetailsListBean.getSpec());
        tvPrice.setText("￥"+orderDetailsListBean.getPrice());
        tvNum.setText("x"+orderDetailsListBean.getQuantity());
    }


    @OnClick({R.id.layout_goods, R.id.tv_refund, R.id.tv_return_the_goods, R.id.tv_refund_and_goods, R.id.tv_after_sales})
    public void onViewClicked(View view) {
        //1退货 2退款 3退货退款 4售后
        switch (view.getId()) {
            case R.id.layout_goods:
                break;
            case R.id.tv_refund:
                OrderProcessingActivity.startActivity(mContext,orderDetailsListBean,orderID,1);
                break;
            case R.id.tv_return_the_goods:
                OrderProcessingActivity.startActivity(mContext,orderDetailsListBean,orderID,2);
                break;
            case R.id.tv_refund_and_goods:
                OrderProcessingActivity.startActivity(mContext,orderDetailsListBean,orderID,3);
                break;
            case R.id.tv_after_sales:
                OrderProcessingActivity.startActivity(mContext,orderDetailsListBean,orderID,4);
                break;
        }
    }


    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }

}