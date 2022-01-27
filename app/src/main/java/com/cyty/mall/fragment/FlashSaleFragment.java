package com.cyty.mall.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cyty.mall.R;
import com.cyty.mall.activity.SeckillGoodsDetailActivity;
import com.cyty.mall.adapter.FlashSaleListAdapter;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.bean.SeckillGoodsInfo;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class FlashSaleFragment extends BaseFragment {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    public static final String TIME = "time";

    private int typeId = 0;
    private int pageIndex = 1;
    private final int pageSize = 10;
    private int total;
    private String time;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;       //正常情况
    private FlashSaleListAdapter mAdapter;
    private List<SeckillGoodsInfo> seckillGoodsInfoList = new ArrayList<>();

    public static FlashSaleFragment newInstance(String time) {
        FlashSaleFragment fragment = new FlashSaleFragment();
        Bundle args = new Bundle();
        args.putString(TIME, time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onNetReload(View v) {
        showLoading();
        selectSchedulingList();
    }

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_flash_sale;
    }

    @Override
    protected void initView() {
        super.initView();
        if (getArguments() != null) {
            time = getArguments().getString(TIME);
        }
    }

    @Override
    protected void initData() {
        super.initData();
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
        mAdapter = new FlashSaleListAdapter(seckillGoodsInfoList);
        recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SeckillGoodsInfo seckillGoodsInfo = seckillGoodsInfoList.get(position);
                if (seckillGoodsInfo.getGoodsId() > 0) {
                    SeckillGoodsDetailActivity.startActivity(mActivity, seckillGoodsInfo.getGoodsId());
                }

            }
        });
        selectSchedulingList();
    }

    /**
     * 获取商品列表
     */
    private void selectSchedulingList() {
        HttpManager.getInstance().selectSchedulingList(time, pageIndex, pageSize,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.selectSchedulingListResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.selectSchedulingListResponse data) {
                        if (state != STATE_MORE) {
                            seckillGoodsInfoList.clear();
                        }
                        if (result) {
                            if (data.data != null) {
                                seckillGoodsInfoList.addAll(data.data);
                                total = totalNum;
                                if (data.data.size() > 0) {
                                    showContent();
                                } else {
                                    showEmpty();
                                }
                            }
                            showSchedulingList();
                        } else {
                            showSchedulingList();
                            if (state != STATE_MORE) {
                                seckillGoodsInfoList.clear();
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
    private void showSchedulingList() {
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
        selectSchedulingList();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        state = STATE_REFRESH;
        pageIndex = 1;
        selectSchedulingList();
    }
}