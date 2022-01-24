package com.cyty.mall.adapter;

import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.SeckillGoodsInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/24 11:59
 * @描述
 */

public class FlashSaleListAdapter extends BaseQuickAdapter<SeckillGoodsInfo, BaseViewHolder> {
    public FlashSaleListAdapter(@Nullable List<SeckillGoodsInfo> data) {
        super(R.layout.item_flash_sale, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SeckillGoodsInfo info) {
        baseViewHolder.setText(R.id.tv_goods_title, info.getTitle())
                .setText(R.id.tv_format, info.getSpec())
                .setText(R.id.tv_price, "￥" + info.getPriceSpike());
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        TextView tvSpecialPrice = baseViewHolder.getView(R.id.tv_special_price);
        TextView tvBuyNow = baseViewHolder.getView(R.id.tv_buy_now);
        TextView tvNum = baseViewHolder.getView(R.id.tv_num);
        ProgressBar progressBar = baseViewHolder.getView(R.id.progress_bar);
        progressBar.setMax(info.getStock());
        progressBar.setProgress(info.getSalesVolume());
        tvSpecialPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvSpecialPrice.setText("￥" + info.getPrice());
        Glide.with(getContext()).load(info.getThumbnail()).into(ivCover);

        DecimalFormat df = new DecimalFormat("##.##%");//传入格式模板
        String result = df.format((float) info.getSalesVolume() / (float) info.getStock());
        tvNum.setText("已抢" + result);
    }
}