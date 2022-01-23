package com.cyty.mall.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

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

    public static FlashSaleFragment newInstance(String time) {
        FlashSaleFragment fragment = new FlashSaleFragment();
        Bundle args = new Bundle();
        args.putString(TIME, time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onNetReload(View v) {

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
        getAppraiseList();
    }

    /**
     * 获取商品评价
     */
    private void getAppraiseList() {
        HttpManager.getInstance().selectSchedulingList(time, pageIndex, pageSize,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.selectSchedulingListResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.selectSchedulingListResponse data) {
//                        if (state != STATE_MORE) {
//                            appraiseInfoList.clear();
//                        }
                        if (result) {
//                            if (data.rows != null) {
//                                tvNum.setText("( " + totalNum + " )");
//                                appraiseInfoList.addAll(data.rows);
//                                total = totalNum;
//                                if (data.rows.size() > 0) {
//                                    showContent();
//                                } else {
//                                    showEmpty();
//                                }
//                            }
//                            showAppraiseList();
                        } else {
//                            showAppraiseList();
//                            if (state != STATE_MORE) {
//                                appraiseInfoList.clear();
//                            }
                            ToastUtils.show(message);
                        }
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }
                });
    }

}