package com.cyty.mall.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseFragment;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

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

    }
}