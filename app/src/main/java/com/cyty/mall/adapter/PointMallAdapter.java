package com.cyty.mall.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.ScoreInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/10 16:54
 * @描述
 */

public class PointMallAdapter extends BaseQuickAdapter<ScoreInfo, BaseViewHolder> {
    public PointMallAdapter(@Nullable List<ScoreInfo> data) {
        super(R.layout.item_points_mall, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ScoreInfo scoreInfo) {
        baseViewHolder.setText(R.id.tvTitle, scoreInfo.getTitle())
                .setText(R.id.tv_score, scoreInfo.getIntegral()+"积分")
                .setText(R.id.tv_stock, "库存");
    }
}
