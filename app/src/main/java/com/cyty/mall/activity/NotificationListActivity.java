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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cyty.mall.R;
import com.cyty.mall.adapter.NewsListAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.NewsInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.event.RefreshAddressListEvent;
import com.cyty.mall.event.RefreshNewsListEvent;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 消息通知列表页面
 */
public class NotificationListActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int id;
    private int pageIndex = 1;
    private final int pageSize = 10;
    private int total;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;       //正常情况
    private List<NewsInfo> newsInfoList = new ArrayList<>();
    private NewsListAdapter mAdapter;

    @Override
    protected void onNetReload(View v) {
        showLoading();
        getNewList();
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvTitle.setText("消息通知");
    }

    public static void startActivity(Context mContext, int id) {
        Intent mIntent = new Intent(mContext, NotificationListActivity.class);
        mIntent.putExtra(Constant.INTENT_ID, id);
        mContext.startActivity(mIntent);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_notification_list;
    }

    @Override
    protected void initView() {
        id = getIntent().getIntExtra(Constant.INTENT_ID, 0);
    }

    @Override
    protected void initData() {
        isUseEventBus(true);
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
        mAdapter = new NewsListAdapter(newsInfoList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                EventBus.getDefault().post(new RefreshNewsListEvent());
                NewsInfo newsInfo = newsInfoList.get(position);
                newsInfo.setNewsRead(2);
                mAdapter.notifyDataSetChanged();
                NotificationDetailActivity.startActivity(mContext, newsInfo.getId());

            }
        });
        getNewList();
    }

    /**
     * 获取消息列表
     */
    private void getNewList() {
        HttpManager.getInstance().getNewList(id, pageIndex, pageSize,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.getNewsListResponse>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.getNewsListResponse data) {
                        if (state != STATE_MORE) {
                            newsInfoList.clear();
                        }
                        if (result) {
                            if (data.rows != null) {
                                newsInfoList.addAll(data.rows);
                                total = totalNum;
                                if (data.rows.size() > 0) {
                                    showContent();
                                } else {
                                    showEmpty();
                                }
                            }
                            showNewsList();
                        } else {
                            showNewsList();
                            if (state != STATE_MORE) {
                                newsInfoList.clear();
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
    private void showNewsList() {
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
        getNewList();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        state = STATE_REFRESH;
        pageIndex = 1;
        getNewList();
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }
}