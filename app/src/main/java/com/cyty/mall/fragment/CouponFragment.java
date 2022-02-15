package com.cyty.mall.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.cyty.mall.MainActivity;
import com.cyty.mall.R;
import com.cyty.mall.adapter.CouponAdapter;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.bean.CouponInfo;
import com.cyty.mall.event.FinishCouponEvent;
import com.cyty.mall.event.MainJumpEvent;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 优惠卷
 */
public class CouponFragment extends BaseFragment {


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

    private CouponAdapter mAdapter;
    private List<CouponInfo> couponInfoList = new ArrayList<>();

    public static CouponFragment newInstance(int type) {
        CouponFragment fragment = new CouponFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_coupon;
    }

    @Override
    protected void onNetReload(View v) {
        showLoading();
        getCouponsList();
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
        mAdapter = new CouponAdapter(couponInfoList, type);
        recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        mAdapter.addChildClickViewIds(R.id.tv_use_type);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.tv_use_type) {
                    EventBus.getDefault().post(new FinishCouponEvent());
                    EventBus.getDefault().post(new MainJumpEvent(1));
                }
            }
        });
        getCouponsList();
    }

    /**
     * 获取优惠券列表
     */
    private void getCouponsList() {
        HttpManager.getInstance().getCouponsList(type, pageIndex, pageSize,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.getCouponsListResponse>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.getCouponsListResponse data) {
                        if (state != STATE_MORE) {
                            couponInfoList.clear();
                        }
                        if (result) {
                            if (data.rows != null) {
                                couponInfoList.addAll(data.rows);
                                total = totalNum;
                                if (data.rows.size() > 0) {
                                    showContent();
                                } else {
                                    showEmpty();
                                }
                            }
                            showCouponsList();
                        } else {
                            showCouponsList();
                            if (state != STATE_MORE) {
                                couponInfoList.clear();
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
    private void showCouponsList() {
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
        getCouponsList();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        state = STATE_REFRESH;
        pageIndex = 1;
        getCouponsList();
    }

}