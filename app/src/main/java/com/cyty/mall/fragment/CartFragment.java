package com.cyty.mall.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cyty.mall.R;
import com.cyty.mall.activity.ConfirmOrderActivity;
import com.cyty.mall.adapter.ShoppingCartAdapter;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.bean.CartGoodsInfo;
import com.cyty.mall.bean.ConfirmOrderInfo;
import com.cyty.mall.contants.MKParameter;
import com.cyty.mall.event.MainJumpEvent;
import com.cyty.mall.event.RefreshUniversalListEvent;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.MkUtils;
import com.hjq.toast.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
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
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll;
    //正常情况

    private List<CartGoodsInfo> cartGoodsInfoList = new ArrayList<>();
    private ShoppingCartAdapter mAdapter;
    //保存数量
    private HashMap<Integer, Integer> maps = new HashMap<>();
    private String ids;
    private boolean chooseAll = false;


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
        isUseEventBus(true);
    }

    @Override
    protected void initData() {
        super.initData();
        if (!StringUtils.isEmpty(MkUtils.decodeString(MKParameter.TOKEN))) {
            selectShoppingCartList();
        }

    }

    /**
     * 获取购物车列表
     */
    private void selectShoppingCartList() {
        HttpManager.getInstance().selectShoppingCartList(1, 1000,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.selectShoppingCartListResponse>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.selectShoppingCartListResponse data) {

                        if (result) {
                            if (data.rows != null) {
                                cartGoodsInfoList = data.rows;
                                initMap();
                                if (data.rows.size() > 0) {
                                    showContent();
                                } else {
                                    showEmpty();
                                }
                            }
                            initAdapter();
                        } else {

                            ToastUtils.show(message);
                        }
                    }

                });

    }

    private void initAdapter() {
        mAdapter = new ShoppingCartAdapter(cartGoodsInfoList);
        recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        mAdapter.addChildClickViewIds(R.id.tv_reduce_num, R.id.tv_add_num);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                CartGoodsInfo cartGoodsInfo = cartGoodsInfoList.get(position);
                int buyNum = maps.get(position);
                if (view.getId() == R.id.tv_add_num) {
                    buyNum++;
                    maps.put(position, buyNum);
                    cartGoodsInfo.setQuantity(buyNum);
                    showBuyData();
                    mAdapter.notifyDataSetChanged();
                } else if (view.getId() == R.id.tv_reduce_num) {
                    if (buyNum > 0) {
                        buyNum--;
                        maps.put(position, buyNum);
                        cartGoodsInfo.setQuantity(buyNum);
                        showBuyData();
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                mAdapter.click(position);
                chooseAll = mAdapter.getAllDataChoiceType();
                if (chooseAll){
                    Drawable drawableLeft = ContextCompat.getDrawable(getContext(),
                            R.drawable.ic_address_selected);
                    tvSelectAll.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                }else {
                    Drawable drawableLeft = ContextCompat.getDrawable(getContext(),
                            R.drawable.ic_address_unselected);
                    tvSelectAll.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                }
                showBuyData();
            }
        });
    }

    private void initMap() {
        maps.clear();
        for (int i = 0; i < cartGoodsInfoList.size(); i++) {
            maps.put(i, cartGoodsInfoList.get(i).getQuantity());
        }
    }


    @OnClick({R.id.tv_select_all, R.id.tv_settlement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_all:
                chooseAll = !chooseAll;
                if (chooseAll){
                    Drawable drawableLeft = ContextCompat.getDrawable(getContext(),
                            R.drawable.ic_address_selected);
                    tvSelectAll.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                }else {
                    Drawable drawableLeft = ContextCompat.getDrawable(getContext(),
                            R.drawable.ic_address_unselected);
                    tvSelectAll.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                }
                mAdapter.setAllDataClickType(chooseAll);
                showBuyData();
                break;
            case R.id.tv_settlement:
                ConfirmOrderActivity.startActivity(mActivity, ids);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void showBuyData() {
        List<CartGoodsInfo> mDataList = mAdapter.getClickData();
        double totalPrice = 0;
        int size = mDataList.size();
        StringBuilder strId = new StringBuilder();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                CartGoodsInfo cartGoodsInfo = mDataList.get(i);
                strId.append(cartGoodsInfo.getSpecId()).append("|");
                strId.append(maps.get(i)).append(",");
                totalPrice = totalPrice + Double.parseDouble(cartGoodsInfo.getPrice()) * cartGoodsInfo.getQuantity();
            }
            ids = strId.toString().substring(0, strId.toString().length() - 1);
            tvTotalGoodsPrice.setText(totalPrice+"");
        } else {
            tvTotalGoodsPrice.setText("0.00");
            ids = "";
        }

    }
    /**
     * 跳转
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUniversalListEvent(RefreshUniversalListEvent event) {
        selectShoppingCartList();
    }
}