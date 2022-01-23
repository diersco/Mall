package com.cyty.mall.adapter;

import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.HomeSecKillGoodsInfo;
import com.cyty.mall.bean.OrderUserInfo;
import com.cyty.mall.util.GlideUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/10 16:54
 * @描述
 */

public class OrderUserAdapter extends BaseQuickAdapter<OrderUserInfo, BaseViewHolder> {
    public OrderUserAdapter(@Nullable List<OrderUserInfo> data) {
        super(R.layout.item_buyers, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderUserInfo orderUserInfo) {
        baseViewHolder.setText(R.id.tv_name, orderUserInfo.getNickname());
        CircleImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        if (!StringUtils.isEmpty(orderUserInfo.getHeadPortrait())){
            GlideUtil.with(getContext()).displayImage(orderUserInfo.getHeadPortrait(), ivCover);
        }
    }
}
