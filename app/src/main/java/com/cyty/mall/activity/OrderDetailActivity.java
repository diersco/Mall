package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.cyty.mall.R;
import com.cyty.mall.adapter.OrderDetailAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.OrderDetailInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.DigitUtil;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单详情页面
 */
public class OrderDetailActivity extends BaseActivity {


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
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.tv_delivery_method)
    TextView tvDeliveryMethod;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_total_goods_price)
    TextView tvTotalGoodsPrice;

    private int id;
    private OrderDetailInfo orderDetailInfo;
    private OrderDetailAdapter mAdapter;
    private List<OrderDetailInfo.OrderDetailsListBean> orderDetailsListBeanList = new ArrayList<>();
    private OrderDetailInfo.AddressesDTOBean addressInfo;

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
        tvTitle.setText("订单详情");
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_order_detail;
    }

    public static void startActivity(Context mContext, int id) {
        Intent mIntent = new Intent(mContext, OrderDetailActivity.class);
        mIntent.putExtra(Constant.INTENT_ID, id);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {
        id = getIntent().getIntExtra(Constant.INTENT_ID, 0);
    }

    @Override
    protected void initData() {

        selectMallOrderDetailsById();
    }

    private void selectMallOrderDetailsById() {
        HttpManager.getInstance().selectMallOrderDetailsById(id,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.selectMallOrderDetailsByIdResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.selectMallOrderDetailsByIdResponse data) {
                        if (result) {
                            orderDetailInfo = data.data;
                            orderDetailsListBeanList = orderDetailInfo.getOrderDetailsList();
                            addressInfo = orderDetailInfo.getAddressesDTO();
                            initBaseDate();
                            initAdapter();
                            initAddress();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void initBaseDate() {
        if (!orderDetailInfo.getPaymentAmount().isEmpty())
            tvTotalGoodsPrice.setText("￥" + orderDetailInfo.getPaymentAmount());
        if (!orderDetailInfo.getFreight().isEmpty())
            tvFreight.setText("￥" + orderDetailInfo.getFreight());
        if (!orderDetailInfo.getOrderTime().isEmpty())
            tvOrderTime.setText(orderDetailInfo.getOrderTime().split(" ")[0]);
    }

    private void initAdapter() {
        mAdapter = new OrderDetailAdapter(orderDetailsListBeanList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        mAdapter.addChildClickViewIds(R.id.tv_buy_again, R.id.tv_after_sales, R.id.tv_evaluate);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                OrderDetailInfo.OrderDetailsListBean orderDetailsListBean = orderDetailsListBeanList.get(position);
                if (view.getId() == R.id.tv_buy_again) {
                    GoodsDetailActivity.startActivity(mContext, orderDetailsListBean.getGoodsId());
                } else if (view.getId() == R.id.tv_after_sales) {
                    if (orderDetailsListBean.getAfterSale() == 1) {
                        //跳转申请售后
                        SelectAftermarketTypeActivity.startActivity(mContext, orderDetailsListBean, id + "");
                    }
                } else if (view.getId() == R.id.tv_evaluate) {
                    if (orderDetailsListBean.getEvaluateStatus() == 2 || orderDetailsListBean.getEvaluateStatus() == 3) {
                        //评价页面
                        OrderCommentActivity.startActivity(mContext);
                    }
                }
            }
        });
    }

    private void initAddress() {
        if (!addressInfo.getName().isEmpty())
            tvUserName.setText(addressInfo.getName());
        if (!addressInfo.getPhone().isEmpty())
            tvUserPhone.setText(DigitUtil.phoneHide(addressInfo.getPhone()));
        if (!addressInfo.getDetailedAddress().isEmpty())
            tvAddress.setText(addressInfo.getDetailedAddress());
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }
}