package com.cyty.mall.fragment;

import android.view.View;

import androidx.core.content.ContextCompat;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseFragment;

import butterknife.BindView;

/**
 * main-购物车
 */
public class CartFragment extends BaseFragment {

//    @BindView(R.id.status_bar)
//    View statusBarView;

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected void initData() {
        super.initData();
//        statusBarView.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.black));
    }
}