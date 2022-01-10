package com.cyty.mall.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyty.mall.R;
import com.cyty.mall.adapter.ClassificationSubpageAdapter;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.bean.GoodsListInfo;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.fondesa.recyclerviewdivider.DividerBuilder;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *main-分类-商品列表
 */
public class ClassificationSubpageFragment extends BaseFragment {


    public static final String TYPE_ID = "type_id";
    @BindView(R.id.recyclerview_classification_subpage)
    RecyclerView recyclerviewClassificationSubpage;
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

    public static ClassificationSubpageFragment newInstance(int typeId) {
        ClassificationSubpageFragment fragment = new ClassificationSubpageFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_ID, typeId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        if (getArguments() != null) {
            typeId = getArguments().getInt(TYPE_ID, 0);
        }
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
        mAdapter = new ClassificationSubpageAdapter(goodsListInfoList);
        recyclerviewClassificationSubpage.setLayoutManager(new GridLayoutManager(mActivity, 2));
        DividerBuilder dividerBuilder = new DividerBuilder(mActivity);
        if (recyclerviewClassificationSubpage.getItemDecorationCount() == 0) {
            dividerBuilder.color(Color.WHITE)
                    .size(5, TypedValue.COMPLEX_UNIT_DIP)
                    .showSideDividers()
                    .build()
                    .addTo(recyclerviewClassificationSubpage);
        }
        recyclerviewClassificationSubpage.setItemAnimator(new DefaultItemAnimator());
        recyclerviewClassificationSubpage.setAdapter(mAdapter);
        getGoodsList();
    }

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_classification_subpage;
    }

    @Override
    protected void onNetReload(View v) {

    }

    /**
     * 获取分类
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
                recyclerviewClassificationSubpage.scrollToPosition(0);
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
}