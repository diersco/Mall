package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.cyty.mall.R;
import com.cyty.mall.adapter.ConfirmOrderAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.AddressInfo;
import com.cyty.mall.bean.ConfirmOrderInfo;
import com.cyty.mall.bean.SignInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.event.DiscountEvent;
import com.cyty.mall.event.GetAddressEvent;
import com.cyty.mall.event.RefreshConfirmOrderAddressEvent;
import com.cyty.mall.event.WeChartPayEvent;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.DigitUtil;
import com.cyty.mall.util.WXPayUtils;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseActivity {


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
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.layout_coupon)
    LinearLayout layoutCoupon;
    @BindView(R.id.tv_delivery_method)
    TextView tvDeliveryMethod;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_total_goods_price)
    TextView tvTotalGoodsPrice;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.iv_ali_select)
    ImageView ivAliSelect;
    @BindView(R.id.layout_ali_pay)
    LinearLayout layoutAliPay;
    @BindView(R.id.iv_wx_select)
    ImageView ivWxSelect;
    @BindView(R.id.layout_wx_pay)
    LinearLayout layoutWxPay;
    @BindView(R.id.tv_buy_now)
    TextView tvBuyNow;

    private String ids;
    private String totalPrice;
    private ConfirmOrderInfo mConfirmOrderInfo;
    private AddressInfo mAddressInfo;
    //购买商品的集合
    private List<ConfirmOrderInfo.ListBean> listBeanList = new ArrayList<>();
    //优惠卷集合
    private List<ConfirmOrderInfo.CouponsListBean> couponsListBeanList = new ArrayList<>();
    private ConfirmOrderAdapter mAdapter;

    //地址id
    private int addressId;
    private int shoppingCart;
    //支付方式 2 支付宝 1 微信  默认支付宝
    private int paymentType = 2;
    private int couponId = -1;

    @Override
    protected void onNetReload(View v) {

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

    @Override
    protected int bindLayout() {
        return R.layout.activity_confirm_order;
    }

    public static void startActivity(Context mContext, String ids) {
        Intent mIntent = new Intent(mContext, ConfirmOrderActivity.class);
        mIntent.putExtra(Constant.INTENT_DATA, ids);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {
        isUseEventBus(true);
        ids = getIntent().getStringExtra(Constant.INTENT_DATA);
        if (!ids.isEmpty()) {
            confirmOrder();
        }
        getDefaultsAddress();
    }

    @Override
    protected void initData() {
        shoppingCart = 2;
        mAdapter = new ConfirmOrderAdapter(listBeanList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
    }

    /**
     * 确认订单信息
     */
    private void confirmOrder() {
        HttpManager.getInstance().confirmOrder(ids,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.confirmOrderResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.confirmOrderResponse data) {
                        if (result) {
                            listBeanList.clear();
                            couponsListBeanList.clear();
                            mConfirmOrderInfo = data.data;
                            initOrderData();
                            totalPrice = data.data.getTotalPrice();
                            listBeanList.addAll(mConfirmOrderInfo.getList());
                            couponsListBeanList.addAll(mConfirmOrderInfo.getCouponsList());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }


    @SuppressLint("SetTextI18n")
    private void initOrderData() {
        if (!mConfirmOrderInfo.getTotalPrice().isEmpty())
            tvTotalGoodsPrice.setText("￥" + mConfirmOrderInfo.getTotalPrice());
        if (!mConfirmOrderInfo.getFreight().isEmpty())
            tvFreight.setText("￥" + mConfirmOrderInfo.getFreight());
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

    /**
     * 创建订单
     */
    private void createOrder() {
        HttpManager.getInstance().createOrder(addressId, ids, shoppingCart, paymentType, couponId,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.createOrderResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.createOrderResponse data) {
                        if (result) {
                            String sign = data.data;
                            if (paymentType == 2) {
                                orderPay(sign);
                            } else {
                                WXOrderPay(sign);
                            }

                        } else {
                            ToastUtils.show(message);

                        }
                    }
                });
    }

    /**
     * 支付宝支付订单
     */
    private void orderPay(String ids) {
        HttpManager.getInstance().orderPay(ids, paymentType + "",
                new HttpEngine.HttpResponseResultCallback<HttpResponse.OrderPayResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.OrderPayResponse data) {
                        if (result) {
                            String sign = data.data;
                            payForAli(sign);
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    /**
     * 微信支付订单
     */
    private void WXOrderPay(String ids) {
        HttpManager.getInstance().WXOrderPay(ids, paymentType + "",
                new HttpEngine.HttpResponseResultCallback<HttpResponse.WXOrderPayResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.WXOrderPayResponse data) {
                        if (result) {
                            SignInfo signInfo = data.data;
                            WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                            builder.setAppId(signInfo.getAppid())
                                    .setPartnerId(signInfo.getPartnerid())
                                    .setPrepayId(signInfo.getPrepayid())
                                    .setPackageValue("Sign=WXPay")
                                    .setNonceStr(signInfo.getNoncestr())
                                    .setTimeStamp(signInfo.getTimestamp())
                                    .setSign(signInfo.getSign())
                                    .build().toWXPayNotSign(ConfirmOrderActivity.this);
                        } else {
                            ToastUtils.show(message);

                        }
                    }
                });
    }

    private void payForAli(final String orderinfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ConfirmOrderActivity.this);
                Map<String, String> result = alipay.payV2(orderinfo, true);
                Message msg = new Message();
                msg.what = 101;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Map<String, String> result = ((Map<String, String>) msg.obj);
            String requestcode = result.get("resultStatus");
            Log.e("测试支付宝回调", requestcode);
            if (!TextUtils.isEmpty(requestcode)) {
                try {
                    switch (Integer.parseInt(requestcode)) {
                        case 9000:
//                            isCanclePay = false;
                            new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        Thread.sleep(200);//
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    finish();
//                                    CoursePaySuccessActivity.startActivity(mContext, orderId);
                                    ToastUtils.show("支付成功");
                                }

                            }.start();
                            break;
                        case 6001:
                            ToastUtils.show("取消支付");
                            break;
                        default:
//                            isCanclePay = false;
                            ToastUtils.show("支付宝支付失败");
                            break;
                    }

                } catch (Exception e) {
                }
            }
        }
    };


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

    @OnClick({R.id.layout_coupon, R.id.tv_no_address, R.id.layout_ali_pay, R.id.layout_wx_pay, R.id.layout_address, R.id.tv_buy_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_coupon:
                GoodsCouponActivity.startActivity(mContext, ids, totalPrice, 1);
                break;
            case R.id.tv_no_address:
            case R.id.layout_address:
                AddressManagementActivity.startActivity(mContext, 2);
                break;
            case R.id.layout_ali_pay:
                paymentType = 2;
                ivAliSelect.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_pay_selected));
                ivWxSelect.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_address_unselected));
                break;
            case R.id.layout_wx_pay:
                paymentType = 1;
                ivAliSelect.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_address_unselected));
                ivWxSelect.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_pay_selected));
                break;
            case R.id.tv_buy_now:
                createOrder();
                break;
        }
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
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
        getDefaultsAddress();
    }


    private long time;

    /**
     * 微信支付结果
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void weiXinPayResult(WeChartPayEvent event) {

        if (System.currentTimeMillis() - time < 1000) {
            return;
        }
        time = System.currentTimeMillis();

        int errCode = event.getErrCode();
        if (errCode == 0) {
//            CoursePaySuccessActivity.startActivity(mContext, orderId);
            finish();
//            EventBus.getDefault().post(new UpdateOrderListEvent());
        } else if (errCode == -2) {
            //用户取消
            ToastUtils.show("取消支付");
        } else if (errCode == -1) {
            //支付失败
            ToastUtils.show("支付失败");
        }
    }

    /**
     * @param event
     */
    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void discountEvent(DiscountEvent event) {
        double price = Double.parseDouble(mConfirmOrderInfo.getTotalPrice()) - event.getDiscount();
        tvTotalGoodsPrice.setText("￥" + price);
        tvCoupon.setText("-￥" + event.getDiscount());
        couponId = event.getCouponId();
    }
}