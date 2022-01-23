package com.cyty.mall.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.activity.PhotoViewActivity;
import com.cyty.mall.bean.AppraiseInfo;
import com.cyty.mall.util.GlideUtil;
import com.fondesa.recyclerviewdivider.DividerBuilder;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/7 16:28
 * @描述
 */

public class ProductEvaluationAdapter extends BaseQuickAdapter<AppraiseInfo, BaseViewHolder> {

    private ImageAdapter imageAdapter;
    private List<String> imgUrls = new ArrayList<>();

    public ProductEvaluationAdapter(@Nullable List<AppraiseInfo> data) {
        super(R.layout.item_product_evaluation, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AppraiseInfo appraiseInfo) {
        baseViewHolder.setText(R.id.tv_name, appraiseInfo.getNickname())
                .setText(R.id.tv_message, appraiseInfo.getComment() + "")
                .setText(R.id.tv_reply, appraiseInfo.getReply() + "")
                .setText(R.id.tv_time, appraiseInfo.getCreateTime());
        ImageView ivHead = baseViewHolder.getView(R.id.iv_head);
        RecyclerView recyclerview = baseViewHolder.getView(R.id.recyclerview);
        if (!StringUtils.isEmpty(appraiseInfo.getHeadPortrait())) {
            GlideUtil.with(getContext()).displayImage(appraiseInfo.getHeadPortrait(), ivHead);
        }
        if (!StringUtils.isEmpty(appraiseInfo.getCommentPicture())) {
            String[] split = appraiseInfo.getCommentPicture().split(",");
            imgUrls = Arrays.asList(split);
            imageAdapter = new ImageAdapter(imgUrls);
            recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
            DividerBuilder dividerBuilder = new DividerBuilder(getContext());
            if (recyclerview.getItemDecorationCount() == 0) {
                dividerBuilder.color(Color.WHITE)
                        .size(5, TypedValue.COMPLEX_UNIT_DIP)
                        .showSideDividers()
                        .build()
                        .addTo(recyclerview);
            }
            recyclerview.setItemAnimator(new DefaultItemAnimator());
            recyclerview.setAdapter(imageAdapter);
            imageAdapter.addChildClickViewIds(R.id.iv_cover);
            imageAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                    Intent intent = new Intent(getContext(), PhotoViewActivity.class);
                    intent.putExtra("currentPosition", position);
                    intent.putExtra("imageList", new Gson().toJson(imgUrls));
                    getContext().startActivity(intent);
                }
            });
        }
    }

}