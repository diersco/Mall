package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyty.mall.R;
import com.cyty.mall.adapter.ClassificationSubpageAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.GoodsListInfo;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.fondesa.recyclerviewdivider.DividerBuilder;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private int typeId = 0;
    private int pageIndex = 1;
    private final int pageSize = 10;
    private int total;
    private String search;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;       //正常情况
    private List<GoodsListInfo> goodsListInfoList = new ArrayList<>();
    private ClassificationSubpageAdapter mAdapter;

    @Override
    protected void onNetReload(View v) {
        showLoading();
        getGoodsList();
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        setLoadSir(refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                search = "";
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
        mAdapter = new ClassificationSubpageAdapter(goodsListInfoList);
        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 2));
        DividerBuilder dividerBuilder = new DividerBuilder(mContext);
        if (recyclerview.getItemDecorationCount() == 0) {
            dividerBuilder.color(Color.WHITE)
                    .size(5, TypedValue.COMPLEX_UNIT_DIP)
                    .showSideDividers()
                    .build()
                    .addTo(recyclerview);
        }
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        getGoodsList();
    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("搜索");
    }

    /**
     * 获取分类数据
     */
    private void getGoodsList() {
        HttpManager.getInstance().getGoodsList(typeId, pageIndex, pageSize, search,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.GoodsListResponse>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.GoodsListResponse data) {
                        if (state != STATE_MORE) {
                            goodsListInfoList.clear();
                        }
                        if (result) {
                            if (data.rows != null) {
                                goodsListInfoList.addAll(data.rows);
                                total = totalNum;
                                if (data.rows.size() > 0) {
                                    showContent();
                                } else {
                                    showEmpty();
                                }
                            }
                            showGoodsList();
                        } else {
                            showGoodsList();
                            if (state != STATE_MORE) {
                                goodsListInfoList.clear();
                            }
                            ToastUtils.show(message);
                        }
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }

                });
    }

    @Override
    protected void initData() {

    }

    /**
     * 展示数据
     */
    private void showGoodsList() {
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
        getGoodsList();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        state = STATE_REFRESH;
        pageIndex = 1;
        getGoodsList();
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        search = etSearch.getText().toString().trim();
        getGoodsList();
    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, SearchActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }
}