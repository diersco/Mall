package com.cyty.mall.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.NewsInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/10 16:54
 * @描述
 */

public class NewsListAdapter extends BaseQuickAdapter<NewsInfo, BaseViewHolder> {
    public NewsListAdapter(@Nullable List<NewsInfo> data) {
        super(R.layout.item_notification_list, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NewsInfo newsInfo) {
        baseViewHolder.setText(R.id.tv_title, newsInfo.getNewsTitle())
                .setText(R.id.tv_message, newsInfo.getNewsContent());
        ImageView ivMessage = baseViewHolder.getView(R.id.iv_message);
        ivMessage.setVisibility(newsInfo.getNewsRead() == 1 ? View.VISIBLE : View.GONE);
    }
}
