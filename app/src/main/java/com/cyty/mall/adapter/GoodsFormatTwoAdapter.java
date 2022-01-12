package com.cyty.mall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cyty.mall.R;
import com.cyty.mall.bean.GoodsInfo;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/12 13:51
 * @描述
 */

public class GoodsFormatTwoAdapter extends TagAdapter {
    Context mContext;
    List<GoodsInfo.SpecListBean.SpecTwoListBean> specListBeanList = new ArrayList<>();
    private TextView tvName;
    private int clickPosition = 0;

    public GoodsFormatTwoAdapter(Context mContext, List<GoodsInfo.SpecListBean.SpecTwoListBean> datas) {
        super(datas);
        this.mContext = mContext;
        this.specListBeanList = datas;
    }

    @Override
    public View getView(FlowLayout parent, int position, Object o) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods_format_text,
                null, false);
        GoodsInfo.SpecListBean.SpecTwoListBean specListBean = specListBeanList.get(position);
        tvName = convertView.findViewById(R.id.tv_name);
        tvName.setText(specListBean.getSpecTwo());
        if (position == clickPosition) {
            tvName.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_round_format_selected));
            tvName.setTextColor(Color.parseColor("#ffffff"));
        } else {
            tvName.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_round_format_unselected));
            tvName.setTextColor(Color.parseColor("#333333"));
        }
        return convertView;
    }

    public void setData(List<GoodsInfo.SpecListBean.SpecTwoListBean> datas) {
        specListBeanList.clear();
        specListBeanList.addAll(datas) ;
    }

    public void setClickPosition(int position) {
        clickPosition = position;
    }
}
