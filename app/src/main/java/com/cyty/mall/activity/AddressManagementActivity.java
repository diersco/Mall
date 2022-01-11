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

import com.bigkoo.alertview.AlertView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cyty.mall.R;
import com.cyty.mall.adapter.AddressListAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.AddressInfo;
import com.cyty.mall.event.RefreshAddressListEvent;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 地址管理
 */
public class AddressManagementActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int typeId;
    private int pageIndex = 1;
    private final int pageSize = 10;
    private int total;
    private String search;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;       //正常情况
    private List<AddressInfo> addressList = new ArrayList<>();
    private AddressListAdapter mAdapter;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_address_management;
    }

    @Override
    protected void initView() {
        isUseEventBus(true);
    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvTitle.setText("地址管理");
    }

    @Override
    protected void initData() {
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
        mAdapter = new AddressListAdapter(addressList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                AddressInfo addressInfo = addressList.get(position);
                AddAddressActivity.startActivity(mContext, addressInfo, 2);
            }
        });
        mAdapter.addChildClickViewIds(R.id.tv_if_default,  R.id.tv_delete);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                AddressInfo addressInfo = addressList.get(position);
                if (view.getId() == R.id.tv_if_default) {//选择默认
                    if (addressInfo.getDefaults() == 1) {
                        reviseDefaultsAddress(2, addressInfo.getId());
                    } else {
                        reviseDefaultsAddress(1, addressInfo.getId());
                    }

                } else if (view.getId() == R.id.tv_delete) {//删除
                    //删除提示框
                    AlertView alertView = new AlertView("提示", "确认删除吗？", null, null, new String[]{"取消", "确定"}, mContext, AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 1) {
                                deleteAddress(addressInfo.getId());
                            }
                        }
                    });
                    alertView.show();

                }


            }
        });
        getAddressList();
    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, AddressManagementActivity.class);
        mContext.startActivity(mIntent);
    }

    @OnClick(R.id.tv_add_address)
    public void onViewClicked() {
        AddAddressActivity.startActivity(mContext, 1);
    }


    /**
     * 获取分类
     */
    private void getAddressList() {
        HttpManager.getInstance().getAddressList(typeId, pageIndex, pageSize, search,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.addressListResponse>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.addressListResponse data) {
                        if (state != STATE_MORE) {
                            addressList.clear();
                        }
                        if (result) {
                            if (data.rows != null) {
                                addressList.addAll(data.rows);
                                total = totalNum;
                                if (data.rows.size() > 0) {
                                    showContent();
                                } else {
                                    showEmpty();
                                }
                            }
                            showAddressList();
                        } else {
                            showAddressList();
                            if (state != STATE_MORE) {
                                addressList.clear();
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
    private void showAddressList() {
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
     * 选择默认地址
     */
    private void reviseDefaultsAddress(int defaults, int id) {
        HttpManager.getInstance().reviseDefaultsAddress(id, defaults,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.reviseDefaultsAddress>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.reviseDefaultsAddress data) {
                        if (result) {
                            getAddressList();
                        } else {
                        }
                    }
                });
    }

    /**
     * 删除地址
     */
    private void deleteAddress(int id) {
        HttpManager.getInstance().deleteAddress(id,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.deleteAddress>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.deleteAddress data) {
                        if (result) {
                            ToastUtils.show("删除成功！");
                            getAddressList();
                        } else {
                            ToastUtils.show("删除失败！");
                        }
                    }
                });
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        state = STATE_MORE;
        pageIndex = ++pageIndex;
        getAddressList();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        state = STATE_REFRESH;
        pageIndex = 1;
        getAddressList();
    }

    /**
     * 刷新列表
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshAddressListEvent(RefreshAddressListEvent event) {
        getAddressList();
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }

}