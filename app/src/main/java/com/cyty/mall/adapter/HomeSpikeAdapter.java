package com.cyty.mall.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.HomeSecKillGoodsInfo;
import com.cyty.mall.bean.NewsInfo;
import com.cyty.mall.util.GlideUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/10 16:54
 * @描述
 */

public class HomeSpikeAdapter extends BaseQuickAdapter<HomeSecKillGoodsInfo.ListBean, BaseViewHolder> {
    public HomeSpikeAdapter(@Nullable List<HomeSecKillGoodsInfo.ListBean> data) {
        super(R.layout.item_home_seckill, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeSecKillGoodsInfo.ListBean listBean) {
        baseViewHolder.setText(R.id.tv_price, "￥"+listBean.getPriceSpike());
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        GlideUtil.with(getContext()).displayImage(listBean.getThumbnail(), ivCover);
    }
}
