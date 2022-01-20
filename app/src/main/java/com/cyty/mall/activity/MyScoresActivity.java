package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyty.mall.R;
import com.cyty.mall.adapter.MyIntegralAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.MyIntegralInfo;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的积分页面
 */
public class MyScoresActivity extends BaseActivity {


    @BindView(R.id.img_unite_return)
    ImageView imgUniteReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_to_exchange)
    TextView tvToExchange;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    private int pageIndex = 1;
    private final int pageSize = 10;
    private int total;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;       //正常情况

    private MyIntegralAdapter mAdapter;
    private List<MyIntegralInfo> myIntegralInfoList = new ArrayList<>();
    private String myIntegral;

    @Override
    protected void onNetReload(View v) {
        showLoading();
        selectMallFlowList();
    }

    public static void startActivity(Context mContext, String myIntegral) {
        Intent mIntent = new Intent(mContext, MyScoresActivity.class);
        mIntent.putExtra(Constant.INTENT_DATA, myIntegral);
        mContext.startActivity(mIntent);
    }


    @Override
    protected int bindLayout() {
        return R.layout.activity_my_scores;
    }

    @Override
    protected void initView() {
        myIntegral = getIntent().getStringExtra(Constant.INTENT_DATA);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        setLoadSir(refreshLayout);
        tvIntegral.setText(myIntegral + "分");
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
        mAdapter = new MyIntegralAdapter(myIntegralInfoList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        selectMallFlowList();
    }

    @Override
    protected void initToolBar() {

    }

    /**
     * 我的积分变动流水列表
     */
    private void selectMallFlowList() {
        HttpManager.getInstance().selectMallFlowList(pageIndex, pageSize,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.selectMallFlowListResponse>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.selectMallFlowListResponse data) {
                        if (state != STATE_MORE) {
                            myIntegralInfoList.clear();
                        }
                        if (result) {
                            if (data.rows != null) {
                                myIntegralInfoList.addAll(data.rows);
                                total = totalNum;
                                if (data.rows.size() > 0) {
                                    showContent();
                                } else {
                                    showEmpty();
                                }
                            }
                            showMallFlowList();
                        } else {
                            showMallFlowList();
                            if (state != STATE_MORE) {
                                myIntegralInfoList.clear();
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
    private void showMallFlowList() {
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
        selectMallFlowList();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        state = STATE_REFRESH;
        pageIndex = 1;
        selectMallFlowList();
    }

    @OnClick(R.id.img_unite_return)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, false);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
    }


    @OnClick(R.id.tv_to_exchange)
    public void onExchangeViewClicked() {
        PointsMallActivity.startActivity(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}