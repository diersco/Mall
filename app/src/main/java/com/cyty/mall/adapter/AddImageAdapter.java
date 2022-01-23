package com.cyty.mall.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AddImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public AddImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_dynamic_release, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String str) {
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        LinearLayout layoutAdd = baseViewHolder.getView(R.id.layout_add);
        if (str.equals("")) {
            //item为添加按钮
            ivCover.setVisibility(View.GONE);
            layoutAdd.setVisibility(View.VISIBLE);
        } else {
            //item为普通图片
            ivCover.setVisibility(View.VISIBLE);
            layoutAdd.setVisibility(View.GONE);
            Glide.with(getContext()).load(str).into(ivCover);
        }
    }
}
