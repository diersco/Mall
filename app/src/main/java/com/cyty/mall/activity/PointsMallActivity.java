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

import com.cyty.mall.R;
import com.cyty.mall.adapter.AddressListAdapter;
import com.cyty.mall.adapter.PointMallAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.ScoreGoodsInfo;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 积分商城
 */
public class PointsMallActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int pageIndex = 1;
    private final int pageSize = 10;
    private int total;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;       //正常情况

    private PointMallAdapter mAdapter;
    private List<ScoreGoodsInfo> scoreGoodsInfoList = new ArrayList<>();

    @Override
    protected void onNetReload(View v) {
        showLoading();
        getIntegralGoodsList();
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_points_mall;
    }

    @Override
    protected void initView() {

    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, PointsMallActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("积分商城");
    }

    @Override
    protected void initData() {
        setLoadSir(refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                int totalPage = total / pageSize + (total % pageSize == 0 ? 0 : 1);
                if (pageIndex < totalPage) {
                    loadMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        });
        mAdapter = new PointMallAdapter(scoreGoodsInfoList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        getIntegralGoodsList();
    }

    /**
     * 积分商城
     */
    private void getIntegralGoodsList() {
        HttpManager.getInstance().getIntegralGoodsList(pageIndex, pageSize,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.getIntegralGoodsListResponse>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.getIntegralGoodsListResponse data) {
                        if (state != STATE_MORE) {
                            scoreGoodsInfoList.clear();
                        }
                        if (result) {
                            if (data.rows != null) {
                                scoreGoodsInfoList.addAll(data.rows);
                                total = totalNum;
                                if (data.rows.size() > 0) {
                                    showContent();
                                } else {
                                    showEmpty();
                                }
                            }
                            showIntegralGoodsList();
                        } else {
                            showIntegralGoodsList();
                            if (state != STATE_MORE) {
                                scoreGoodsInfoList.clear();
                            }
                            ToastUtils.show(message);
                        }
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }

                });

    }
    /**
     * 展示数据
     */
    private void showIntegralGoodsList() {
        switch (state) {
            case STATE_NORMAL:
            case STATE_MORE:
                mAdapter.notifyDataSetChanged();
                break;
            case STATE_REFRESH:
                mAdapter.notifyDataSetChanged();
                recyclerview.scrollToPosition(0);
                break;
        }
    }
    /**
     * 加载更多
     */
    private void loadMoreData() {
        state = STATE_MORE;
        pageIndex = ++pageIndex;
        getIntegralGoodsList();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        state = STATE_REFRESH;
        pageIndex = 1;
        getIntegralGoodsList();
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }

}