package com.cyty.mall.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.LogisticsInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/24 11:03
 * @描述
 */

public class LogisticsInformationAdapter extends BaseQuickAdapter<LogisticsInfo.DataBean, BaseViewHolder> {
    List<LogisticsInfo.DataBean> datas = new ArrayList<>();
    public LogisticsInformationAdapter(@Nullable List<LogisticsInfo.DataBean> data) {
        super(R.layout.item_logistics_information, data);
        this.datas = data;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LogisticsInfo.DataBean dataBean) {
        baseViewHolder.setText(R.id.text_label, dataBean.getStatus())
                .setText(R.id.text_time, dataBean.getTime())
                .setText(R.id.text_message, dataBean.getContext());
        View lineTop = baseViewHolder.getView(R.id.line_top);
        View lineBoom = baseViewHolder.getView(R.id.line_bottom);
        ImageView ivType = baseViewHolder.getView(R.id.iv_type);
        if (baseViewHolder.getAdapterPosition() == 0) {
            lineTop.setVisibility(View.INVISIBLE);
        }
        if (baseViewHolder.getAdapterPosition() == datas.size() - 1)
        {
            lineBoom.setVisibility(View.INVISIBLE);
        }
        switch (dataBean.getStatusCode()) {
            case "1":
            case "101":
            case "102":
            case "103":
                ivType.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_logistics_order));
                break;
            case "0":
            case "1001":
            case "1002":
            case "1003":
                ivType.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_logistics_transportation));
                break;
            case "5":
            case "501":
                ivType.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_logistics_delivery));
                break;
            case "3":
            case "301":
            case "302":
            case "303":
            case "304":
                ivType.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_logistics_sign_for_receipt));
                break;
        }
    }
}