package com.cyty.mall.adapter;

import android.util.SparseArray;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.CartGoodsInfo;
import com.cyty.mall.util.GlideUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCartAdapter extends BaseQuickAdapter<CartGoodsInfo, BaseViewHolder> {
    SparseArray<CartGoodsInfo> goodsInfoList = new SparseArray<>();
    private List<CartGoodsInfo> mDataList;
    private HashMap<Integer, Boolean> clickData = new HashMap<>();
    private HashMap<Integer, Integer> mps = new HashMap<>();

    public ShoppingCartAdapter(@Nullable List<CartGoodsInfo> data) {
        super(R.layout.item_cart, data);
        this.mDataList = data;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CartGoodsInfo goodsInfo) {
        baseViewHolder.setText(R.id.tv_title, goodsInfo.getGoodsTitle())
                .setText(R.id.tv_format, goodsInfo.getSpec())
                .setText(R.id.tv_buy_num, goodsInfo.getQuantity() + "")
                .setText(R.id.tv_price, goodsInfo.getPrice());
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        ImageView ivSelect = baseViewHolder.getView(R.id.iv_select);
        GlideUtil.with(getContext()).displayImage(goodsInfo.getThumbnail(), ivCover);
        if (clickData.get(baseViewHolder.getAdapterPosition()) == null) {
            int a = baseViewHolder.getAdapterPosition();
            clickData.put(baseViewHolder.getAdapterPosition(), false);
        }
        if (clickData.get(baseViewHolder.getAdapterPosition())) {
            goodsInfoList.put(baseViewHolder.getLayoutPosition(), goodsInfo);
            ivSelect.setImageResource(R.drawable.ic_address_selected);
        } else {
            goodsInfoList.delete(baseViewHolder.getLayoutPosition());
            ivSelect.setImageResource(R.drawable.ic_address_unselected);
        }
    }

    public void setAllDataClickType(boolean type) {
        for (int i = 0; i < mDataList.size(); i++) {
            clickData.put(i, type);
        }
        notifyDataSetChanged();
    }

    public List<CartGoodsInfo> getClickData() {
        List<CartGoodsInfo> listData = new ArrayList<>();
        for (int i = 0; i < mDataList.size(); i++) {
            if (clickData.get(i)) {
                listData.add(mDataList.get(i));
            }
        }

        return listData;
    }

    public void click(int position) {
        clickData.put(position, !clickData.get(position));
        notifyItemChanged(position);
    }

    public boolean getAllDataChoiceType() {
        for (int i = 0; i < mDataList.size(); i++) {
            if (clickData.get(i) == null || !clickData.get(i)) {
                return false;
            }

        }
        return true;
    }

    public SparseArray<CartGoodsInfo> getSparse() {
        return goodsInfoList;
    }
}