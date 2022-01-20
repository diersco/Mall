package com.cyty.mall.adapter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.MyIntegralInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/13 16:15
 * @描述
 */
public class MyIntegralAdapter extends BaseQuickAdapter<MyIntegralInfo, BaseViewHolder> {


    public MyIntegralAdapter(@Nullable List<MyIntegralInfo> data) {
        super(R.layout.item_my_scores, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyIntegralInfo myIntegralInfo) {
        baseViewHolder.setText(R.id.tv_title, myIntegralInfo.getChangeReason())
                .setText(R.id.tv_time, myIntegralInfo.getChangeTime());
        TextView tvScore = baseViewHolder.getView(R.id.tv_score);
        if (myIntegralInfo.getChangeType() == 1) {
            tvScore.setText("+" + myIntegralInfo.getChanges());
        } else {
            tvScore.setText("-" + myIntegralInfo.getChanges());
        }
    }
}
