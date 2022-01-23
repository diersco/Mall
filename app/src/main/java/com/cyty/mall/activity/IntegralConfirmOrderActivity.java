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
import com.cyty.mall.event.GetAddressEvent;
import com.cyty.mall.event.RefreshConfirmOrderAddressEvent;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.DigitUtil;
import com.cyty.mall.util.GlideUtil;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 积分确认订单
 */
public class IntegralConfirmOrderActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.layout_address)
    LinearLayout layoutAddress;
    @BindView(R.id.tv_no_address)
    TextView tvNoAddress;
    @BindView(R.id.tv_buy_now)
    TextView tvBuyNow;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_format)
    TextView tvGoodsFormat;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_buy_num)
    TextView tvBuyNum;
    private String specId;
    //地址id
    private int addressId;
    private AddressInfo mAddressInfo;
    private String title;
    private String imgUrl;
    private String spec;
    private int integral;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_integral_confirm_order;
    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("确认订单");
    }

    public static void startActivity(Context mContext, String title, String imgUrl, String spec, int integral, String specID) {
        Intent mIntent = new Intent(mContext, IntegralConfirmOrderActivity.class);
        mIntent.putExtra("title", title);
        mIntent.putExtra("imgUrl", imgUrl);
        mIntent.putExtra("spec", spec);
        mIntent.putExtra("integral", integral);
        mIntent.putExtra("specID", specID);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {
        isUseEventBus(true);
        title = getIntent().getStringExtra("title");
        imgUrl = getIntent().getStringExtra("imgUrl");
        spec = getIntent().getStringExtra("spec");
        specId = getIntent().getStringExtra("specID");
        integral = getIntent().getIntExtra("integral", 0);
        getDefaultsAddress();
    }

    @Override
    protected void initData() {
        tvGoodsName.setText(title);
        tvGoodsPrice.setText(integral + "积分");
        tvGoodsFormat.setText(spec);
        tvBuyNum.setText("x1");
        GlideUtil.with(mContext).displayImage(imgUrl, ivCover);
    }

    /**
     * 获取默认地址
     */
    private void getDefaultsAddress() {
        HttpManager.getInstance().getDefaultsAddress(
                new HttpEngine.HttpResponseResultCallback<HttpResponse.getDefaultsAddressResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.getDefaultsAddressResponse data) {
                        if (result) {
                            mAddressInfo = data.data;
                            addressId = mAddressInfo.getId();
                            initAddress();
                        } else {
                            if (data.msg.equals("无数据")) {
                                layoutAddress.setVisibility(View.GONE);
                                tvNoAddress.setVisibility(View.VISIBLE);
                            } else {
                                ToastUtils.show(message);
                            }

                        }
                    }
                });
    }

    private void initAddress() {
        layoutAddress.setVisibility(View.VISIBLE);
        tvNoAddress.setVisibility(View.GONE);
        if (!mAddressInfo.getName().isEmpty())
            tvUserName.setText(mAddressInfo.getName());
        if (!mAddressInfo.getPhone().isEmpty())
            tvUserPhone.setText(DigitUtil.phoneHide(mAddressInfo.getPhone()));
        if (!mAddressInfo.getDetailedAddress().isEmpty())
            tvAddress.setText(mAddressInfo.getDetailedAddress());
    }

    /**
     * 立即兑换
     */
    private void pointsExchangeCommodity() {
        HttpManager.getInstance().pointsExchangeCommodity(addressId + "", specId,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.pointsExchangeCommodityResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.pointsExchangeCommodityResponse data) {
                        if (result) {
                            ToastUtils.show("兑换成功");
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    @OnClick(R.id.tv_buy_now)
    public void onViewClicked() {
        pointsExchangeCommodity();
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }

    @OnClick({R.id.layout_address, R.id.tv_no_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_address:
            case R.id.tv_no_address:
                AddressManagementActivity.startActivity(mContext, 2);
                break;
        }
    }
    /**
     * 获取返回的地址数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAddressEvent(GetAddressEvent event) {
        mAddressInfo = event.getAddressInfo();
        addressId = mAddressInfo.getId();
        initAddress();
    }
    /**
     * 刷新地址数据
     * 进入地址管理页面后做的处理（删除，修改，修改默认值）
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshConfirmOrderAddressEvent(RefreshConfirmOrderAddressEvent event) {
//        getDefaultsAddress();
    }
}