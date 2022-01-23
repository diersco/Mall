package com.cyty.mall.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.ProblemInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class CommonProblemAdapter extends BaseQuickAdapter<ProblemInfo, BaseViewHolder> {
    //是否展开
    private boolean isUnfold = false;

    public CommonProblemAdapter(@Nullable List<ProblemInfo> data) {
        super(R.layout.item_contact_customer_service, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProblemInfo collectionInfo) {
        baseViewHolder.setText(R.id.tv_title, collectionInfo.getProblemTitle())
                .setText(R.id.tv_message, collectionInfo.getReplyContent());
        TextView tvExpand = baseViewHolder.getView(R.id.tv_expand);
        TextView tvMessage = baseViewHolder.getView(R.id.tv_message);
        tvExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUnfold) {
                    isUnfold = false;
                    Drawable drawableLeft = ContextCompat.getDrawable(getContext(),
                            R.drawable.ic_entity_arrow_right);
                    tvExpand.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                    tvExpand.setText("展开");
                    tvMessage.setVisibility(View.GONE);
                } else {
                    isUnfold = true;
                    Drawable drawableLeft = ContextCompat.getDrawable(getContext(),
                            R.drawable.ic_entity_arrow_down);
                    tvExpand.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                    tvExpand.setText("收起");
                    tvMessage.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}