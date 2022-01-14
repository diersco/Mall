package com.cyty.mall.activity;

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
import com.cyty.mall.adapter.GoodsCouponAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.ConfirmOrderInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商品优惠卷
 */
public class GoodsCouponActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private String ids;
    //选择的优惠卷id
    private int couponId;
    //总金额
    private String totalPrice;
    private ConfirmOrderInfo mConfirmOrderInfo;
    //优惠卷集合
    private List<ConfirmOrderInfo.CouponsListBean> couponsListBeanList = new ArrayList<>();
    private GoodsCouponAdapter mAdapter;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("优惠券");
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_goods_coupon;
    }

    public static void startActivity(Context mContext, String ids,String totalPrice) {
        Intent mIntent = new Intent(mContext, GoodsCouponActivity.class);
        mIntent.putExtra(Constant.INTENT_DATA, ids);
        mIntent.putExtra(Constant.INTENT_PRICE, totalPrice);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {
        ids = getIntent().getStringExtra(Constant.INTENT_DATA);
        totalPrice = getIntent().getStringExtra(Constant.INTENT_PRICE);
        confirmOrder();
    }

    @Override
    protected void initData() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                confirmOrder();
            }
        });
        mAdapter = new GoodsCouponAdapter(couponsListBeanList);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        mAdapter.addChildClickViewIds(R.id.tv_use_type);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                ConfirmOrderInfo.CouponsListBean couponsListBean = couponsListBeanList.get(position);
                if (view.getId() == R.id.tv_use_type) {//选择默认
                    calculatedAmount();
                }

            }
        });

        confirmOrder();
    }

    /**
     * 确认订单信息(获取优惠卷列表)
     * list数据不好传，所以从新获取一遍
     */
    private void confirmOrder() {
        HttpManager.getInstance().confirmOrder(ids,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.confirmOrderResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.confirmOrderResponse data) {
                        if (result) {
                            couponsListBeanList.clear();
                            mConfirmOrderInfo = data.data;
                            couponsListBeanList.addAll(mConfirmOrderInfo.getCouponsList());
                            mAdapter.notifyDataSetChanged();
                            recyclerview.scrollToPosition(0);
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    /**
     * 计算金额
     */
    private void calculatedAmount() {
        HttpManager.getInstance().calculatedAmount(couponId, totalPrice,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.calculatedAmountResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.calculatedAmountResponse data) {
                        if (result) {

                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }

}