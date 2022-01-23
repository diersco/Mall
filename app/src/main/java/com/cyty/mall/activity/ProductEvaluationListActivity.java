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

import com.cyty.mall.R;
import com.cyty.mall.adapter.ProductEvaluationAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.AppraiseInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商品评价列表
 */
public class ProductEvaluationListActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int pageIndex = 1;
    private final int pageSize = 10;
    private int total;
    private String search;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;       //正常情况

    private int goodsID;
    private ProductEvaluationAdapter mAdapter;
    private List<AppraiseInfo> appraiseInfoList = new ArrayList<>();

    @Override
    protected void onNetReload(View v) {
        showLoading();
        getAppraiseList();
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_product_evaluation_list;
    }

    @Override
    protected void initView() {
        goodsID = getIntent().getIntExtra(Constant.INTENT_ID, 0);
    }

    @Override
    protected void initToolBar() {
        //设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("商品评价列表");
    }

    public static void startActivity(Context mContext, int goodsID) {
        Intent mIntent = new Intent(mContext, ProductEvaluationListActivity.class);
        mIntent.putExtra(Constant.INTENT_ID, goodsID);
        mContext.startActivity(mIntent);
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
        mAdapter = new ProductEvaluationAdapter(appraiseInfoList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        getAppraiseList();
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        state = STATE_MORE;
        pageIndex = ++pageIndex;
        getAppraiseList();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        state = STATE_REFRESH;
        pageIndex = 1;
        getAppraiseList();
    }

    /**
     * 获取商品评价
     */
    private void getAppraiseList() {
        HttpManager.getInstance().getAppraiseList(goodsID, pageIndex, pageSize,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.getAppraiseListResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.getAppraiseListResponse data) {
                        if (state != STATE_MORE) {
                            appraiseInfoList.clear();
                        }
                        if (result) {
                            if (data.rows != null) {
                                tvNum.setText("( " + totalNum + " )");
                                appraiseInfoList.addAll(data.rows);
                                total = totalNum;
                                if (data.rows.size() > 0) {
                                    showContent();
                                } else {
                                    showEmpty();
                                }
                            }
                            showAppraiseList();
                        } else {
                            showAppraiseList();
                            if (state != STATE_MORE) {
                                appraiseInfoList.clear();
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
    private void showAppraiseList() {
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

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }

}