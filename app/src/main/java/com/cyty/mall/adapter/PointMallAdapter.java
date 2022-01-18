package com.cyty.mall.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.ScoreGoodsInfo;
import com.cyty.mall.util.GlideUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/10 16:54
 * @描述
 */

public class PointMallAdapter extends BaseQuickAdapter<ScoreGoodsInfo, BaseViewHolder> {
    public PointMallAdapter(@Nullable List<ScoreGoodsInfo> data) {
        super(R.layout.item_points_mall, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ScoreGoodsInfo scoreInfo) {
        baseViewHolder.setText(R.id.tv_title, scoreInfo.getTitle())
                .setText(R.id.tv_score, scoreInfo.getIntegral()+"积分")
                .setText(R.id.tv_sales_volume, "销量："+scoreInfo.getSalesVolume());
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        GlideUtil.with(getContext()).displayImage(scoreInfo.getThumbnail(), ivCover);
    }
}
