package com.cyty.mall.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.cyty.mall.R;
import com.cyty.mall.activity.GoodsDetailActivity;
import com.cyty.mall.activity.LogisticsInformationActivity;
import com.cyty.mall.activity.OrderDetailActivity;
import com.cyty.mall.adapter.OrderListAdapter;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.bean.OrderListInfo;
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

/**
 * 订单
 */
public class OrderFragment extends BaseFragment {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private static final String ARG_TYPE = "type";
    private int pageIndex = 1;
    private final int pageSize = 10;
    private int total;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;       //正常情况
    private int type;
    private OrderListAdapter mAdapter;
    private List<OrderListInfo> orderListInfoList = new ArrayList<>();

    public static OrderFragment newInstance(int type) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_order;
    }

    @Override
    protected void onNetReload(View v) {
        showLoading();
        selectMallPaymentOrderList();
    }

    @Override
    protected void initView() {
        super.initView();
        setLoadSir(refreshLayout);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_TYPE, 0);
        }
    }

    @Override
    protected void initData() {
        super.initData();
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
        mAdapter = new OrderListAdapter(orderListInfoList);
        recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        mAdapter.addChildClickViewIds(R.id.tv_line_gary, R.id.tv_round_purple);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                OrderListInfo orderListInfo = orderListInfoList.get(position);
                int state = orderListInfo.getOrderStatus();
                if (view.getId() == R.id.tv_line_gary) {
                    if (state == 1) {
                        GoodsDetailActivity.startActivity(mActivity, orderListInfo.getOrderDetailsList().get(0).getGoodsId());
                    } else if (state == 3) {
                        //查看物流
                        LogisticsInformationActivity.startActivity(mActivity,orderListInfo.getId());
                    }
                } else if (view.getId() == R.id.tv_round_purple) {
                    if (state == 1) {
                        //立即支付
                    } else if (state == 2 || state == 4) {
                        OrderDetailActivity.startActivity(mActivity, orderListInfo.getId());
                        //查看详情
                    } else if (state == 3) {
                        //确认收货
                    }
                }
            }
        });
        selectMallPaymentOrderList();
    }

    /**
     * 获取购物车列表
     */
    private void selectMallPaymentOrderList() {
        HttpManager.getInstance().selectMallPaymentOrderList(type, pageIndex, pageSize,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.selectMallPaymentOrderListResponse>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.selectMallPaymentOrderListResponse data) {
                        if (state != STATE_MORE) {
                            orderListInfoList.clear();
                        }
                        if (result) {
                            if (data.rows != null) {
                                orderListInfoList.addAll(data.rows);
                                total = totalNum;
                                if (data.rows.size() > 0) {
                                    showContent();
                                } else {
                                    showEmpty();
                                }
                            }
                            showOrderList();
                        } else {
                            showOrderList();
                            if (state != STATE_MORE) {
                                orderListInfoList.clear();
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
    private void showOrderList() {
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
        selectMallPaymentOrderList();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        state = STATE_REFRESH;
        pageIndex = 1;
        selectMallPaymentOrderList();
    }
}