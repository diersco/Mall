package com.cyty.mall.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.cyty.mall.R;
import com.cyty.mall.adapter.AddressListAdapter;
import com.cyty.mall.adapter.ShoppingCartAdapter;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.bean.CartGoodsInfo;
import com.cyty.mall.bean.GoodsInfo;
import com.cyty.mall.contants.MKParameter;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.MkUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * main-购物车
 */
public class CartFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_total_goods_price)
    TextView tvTotalGoodsPrice;
    @BindView(R.id.tv_is_freight)
    TextView tvIsFreight;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll;
    private int pageIndex = 1;
    private final int pageSize = 10;
    private int total;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;       //正常情况

    private List<CartGoodsInfo> cartGoodsInfoList = new ArrayList<>();
    private ShoppingCartAdapter mAdapter;
    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected void initView() {
        super.initView();
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
        mAdapter = new ShoppingCartAdapter(cartGoodsInfoList);
        recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        if (!StringUtils.isEmpty(MkUtils.decodeString(MKParameter.TOKEN))){
            selectShoppingCartList();
        }

    }

    /**
     * 获取购物车列表
     */
    private void selectShoppingCartList() {
        HttpManager.getInstance().selectShoppingCartList(pageIndex, pageSize,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.selectShoppingCartListResponse>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.selectShoppingCartListResponse data) {
                        if (state != STATE_MORE) {
                            cartGoodsInfoList.clear();
                        }
                        if (result) {
                            if (data.rows != null) {
                                cartGoodsInfoList.addAll(data.rows);
                                total = totalNum;
                                if (data.rows.size() > 0) {
                                    showContent();
                                } else {
                                    showEmpty();
                                }
                            }
                            showShoppingCart();
                        } else {
                            showShoppingCart();
                            if (state != STATE_MORE) {
                                cartGoodsInfoList.clear();
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
    private void showShoppingCart() {
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
        selectShoppingCartList();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        state = STATE_REFRESH;
        pageIndex = 1;
        selectShoppingCartList();
    }

    @OnClick({R.id.tv_select_all, R.id.tv_settlement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_all:
                break;
            case R.id.tv_settlement:
                break;
        }
    }
}