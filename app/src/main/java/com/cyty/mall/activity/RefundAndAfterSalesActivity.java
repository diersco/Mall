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
import com.cyty.mall.adapter.RefundAndAfterSalesAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.AfterSalesInfo;
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
 * 退款与售后页面
 */
public class RefundAndAfterSalesActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<AfterSalesInfo> afterSalesInfoList = new ArrayList<>();
    private RefundAndAfterSalesAdapter mAdapter;

    @Override
    protected void onNetReload(View v) {
        showLoading();
        selectMallExchangeList();
    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("退款与售后");
    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, RefundAndAfterSalesActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_refund_and_after_sales;
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
    }


    @Override
    protected void initData() {
        selectMallExchangeList();
    }

    /**
     * 退款与售后
     */
    private void selectMallExchangeList() {
        HttpManager.getInstance().getMallAftermarketList(
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.getMallAftermarketListResponse>() {
                    @Override
                    public void onResponse(boolean result, int total, String message, HttpResponse.getMallAftermarketListResponse data) {

                        if (result) {
                            afterSalesInfoList = data.rows;
                            if (afterSalesInfoList.size() > 0) {
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
        mAdapter = new RefundAndAfterSalesAdapter(afterSalesInfoList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        mAdapter.addChildClickViewIds(R.id.tv_btn_gary_left, R.id.tv_btn_purple);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                AfterSalesInfo afterSalesInfo = afterSalesInfoList.get(position);
                if (view.getId() == R.id.tv_btn_gary_left) {
                    //删除记录
                    deleteAfterSale(afterSalesInfo.getId());
                } else if (view.getId() == R.id.tv_btn_purple) {
                    switch (afterSalesInfo.getAfterSale()) {
                        case 2:
                            //取消售后
                            cancelAfterSale(afterSalesInfo.getId());
                            break;
                        case 3:
                            RefundDetailsActivity.startActivity(mContext, afterSalesInfo.getId() + "", 1);
                            break;
                        case 4:
                            //查看反馈
                            RefundDetailsActivity.startActivity(mContext, afterSalesInfo.getId() + "", 2);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    /**
     * 删除售后记录
     */
    private void deleteAfterSale(int id) {
        HttpManager.getInstance().deleteAfterSale(id,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.deleteAfterSaleResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.deleteAfterSaleResponse data) {
                        if (result) {
                            ToastUtils.show("删除成功");
                            selectMallExchangeList();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    /**
     * 删除售后记录
     */
    private void cancelAfterSale(int id) {
        HttpManager.getInstance().cancelAfterSale(id,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.cancelAfterSaleResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.cancelAfterSaleResponse data) {
                        if (result) {
                            ToastUtils.show("取消成功");
                            selectMallExchangeList();
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