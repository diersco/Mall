package com.cyty.mall.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.CollectionInfo;
import com.cyty.mall.util.GlideUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/13 16:15
 * @描述
 */
public class CollectionAdapter extends BaseQuickAdapter<CollectionInfo, BaseViewHolder> {


    public CollectionAdapter(@Nullable List<CollectionInfo> data) {
        super(R.layout.item_collection, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CollectionInfo collectionInfo) {
        baseViewHolder.setText(R.id.tv_goods_name, collectionInfo.getGoodsTitle())
                .setText(R.id.tv_goods_format, collectionInfo.getSpec())
                .setText(R.id.tv_goods_price, "￥" + collectionInfo.getPrice());
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        GlideUtil.with(getContext()).displayImage(collectionInfo.getThumbnail(), ivCover);
    }
}
