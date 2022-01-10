package com.cyty.mall.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.AddressInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/10 16:54
 * @描述
 */

public class AddressListAdapter extends BaseQuickAdapter<AddressInfo, BaseViewHolder> {
    public AddressListAdapter( @Nullable List<AddressInfo> data) {
        super(R.layout.item_address_management, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AddressInfo addressInfo) {
        baseViewHolder.setText(R.id.tv_name, addressInfo.getName())
                .setText(R.id.tv_phone_num, addressInfo.getPhone())
                .setText(R.id.tv_address, addressInfo.getDetailedAddress() + "人购买");
        ImageView ivEdit = baseViewHolder.getView(R.id.iv_edit);
        TextView tvCover = baseViewHolder.getView(R.id.tv_cover);
        TextView tvIfDefault = baseViewHolder.getView(R.id.tv_if_default);
        TextView tvDefault = baseViewHolder.getView(R.id.tv_default);
        tvCover.setText(addressInfo.getName().substring(0, 1));
    }
}
