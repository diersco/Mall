package com.cyty.mall.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.CouponInfo;
import com.cyty.mall.bean.HomeDataInfo;
import com.cyty.mall.util.GlideUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/19 10:29
 * @描述
 */
public class FrontImageAdapter extends BaseQuickAdapter<HomeDataInfo.PosterListBean, BaseViewHolder> {
    public FrontImageAdapter( @Nullable List<HomeDataInfo.PosterListBean> data) {
        super(R.layout.item_fornt_img, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeDataInfo.PosterListBean posterListBean) {
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        GlideUtil.with(getContext()).displayImage(posterListBean.getResourceLink(), ivCover);
    }
}
