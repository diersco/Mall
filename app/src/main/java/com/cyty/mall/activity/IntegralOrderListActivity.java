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
import com.cyty.mall.adapter.IntegralOrderListAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.IntegralOrderInfo;
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
 * 积分订单列表
 */
public class IntegralOrderListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<IntegralOrderInfo> integralOrderInfoList = new ArrayList<>();
    private IntegralOrderListAdapter mAdapter;

    @Override
    protected void onNetReload(View v) {
        showContent();
        selectMallExchangeList();
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_integral_order_list;
    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, IntegralOrderListActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvTitle.setText("积分订单");
    }

    @Override
    protected void initView() {
        setLoadSir(refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                selectMallExchangeList();
            }
        });
        selectMallExchangeList();
    }

    @Override
    protected void initData() {

    }

    /**
     * 兑换列表
     */
    private void selectMallExchangeList() {
        HttpManager.getInstance().selectMallExchangeList(
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.selectMallExchangeListResponse>() {
                    @Override
                    public void onResponse(boolean result, int total, String message, HttpResponse.selectMallExchangeListResponse data) {

                        if (result) {
                            integralOrderInfoList = data.rows;
                            if (integralOrderInfoList.size() > 0) {
                                showContent();
                            } else {
                                showEmpty();
                            }
                            initAdapter();
                        } else {
                            ToastUtils.show(message);
                        }
                        refreshLayout.finishRefresh();
                    }
                });
    }

    private void initAdapter() {
        mAdapter = new IntegralOrderListAdapter(integralOrderInfoList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        mAdapter.addChildClickViewIds(R.id.tv_line_gary);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                IntegralOrderInfo integralOrderInfo = integralOrderInfoList.get(position);
                if (view.getId() == R.id.tv_line_gary) {
                    LogisticsInformationActivity.startActivity(mContext, integralOrderInfo.getId());
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