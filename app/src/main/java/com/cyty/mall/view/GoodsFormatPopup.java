package com.cyty.mall.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyty.mall.R;
import com.cyty.mall.adapter.GoodsFormatAdapter;
import com.cyty.mall.adapter.GoodsFormatTwoAdapter;
import com.cyty.mall.bean.GoodsInfo;
import com.cyty.mall.util.GlideUtil;
import com.hjq.toast.ToastUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/12 13:38
 * @描述
 */
public class GoodsFormatPopup extends BasePopupWindow {

    ImageView ivCover;
    TextView tvTitle;
    TextView tvStock;
    TextView tvPrice;
    TagFlowLayout tlFormatOne;
    TagFlowLayout tlFormatTwo;
    TextView tvSure;
    TextView tvReduceNum;
    TextView tvBuyNum;
    TextView tvAddNum;


    private Context mContext;
    private GoodsInfo mGoodsInfo;
    private GoodsFormatAdapter mGoodsFormatAdapter;
    private GoodsFormatTwoAdapter mGoodsFormatTwoAdapter;
    private List<GoodsInfo.SpecListBean> specListBeanList = new ArrayList<>();
    private List<GoodsInfo.SpecListBean.SpecTwoListBean> specTwoListList = new ArrayList<>();
    private int buyNum = 1;
    private int totalStock;

    public GoodsFormatPopup(Context context, GoodsInfo goodsInfo) {
        super(context);
        this.mContext = context;
        this.mGoodsInfo = goodsInfo;
        setContentView(R.layout.layout_goods_popup);
        setPopupGravity(Gravity.BOTTOM);
        initGoodsData(mGoodsInfo);

    }


    private void initGoodsData(GoodsInfo mGoodsInfo) {
        ivCover = findViewById(R.id.iv_cover);
        tvTitle = findViewById(R.id.tv_title);
        tvStock = findViewById(R.id.tv_stock);
        tvPrice = findViewById(R.id.tv_price);
        tvAddNum = findViewById(R.id.tv_add_num);
        tvReduceNum = findViewById(R.id.tv_reduce_num);
        tvBuyNum = findViewById(R.id.tv_buy_num);
        tlFormatOne = findViewById(R.id.tl_format_one);
        tlFormatTwo = findViewById(R.id.tl_format_two);
        tvSure = findViewById(R.id.tv_sure);
        specListBeanList = mGoodsInfo.getSpecList();
        mGoodsFormatAdapter = new GoodsFormatAdapter(mContext, specListBeanList);
        tlFormatOne.setAdapter(mGoodsFormatAdapter);
        specTwoListList = specListBeanList.get(0).getSpecTwoList();
        initFormatData(specListBeanList.get(0).getSpecTwoList().get(0));
        mGoodsFormatTwoAdapter = new GoodsFormatTwoAdapter(mContext, specTwoListList);
        tlFormatTwo.setAdapter(mGoodsFormatTwoAdapter);
        tlFormatOne.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                GoodsInfo.SpecListBean specListBean = specListBeanList.get(position);
                specTwoListList = specListBean.getSpecTwoList();
                mGoodsFormatAdapter.setClickPosition(position);
                mGoodsFormatAdapter.notifyDataChanged();
                specTwoListList = specListBeanList.get(position).getSpecTwoList();
                initFormatData(specListBeanList.get(position).getSpecTwoList().get(0));
                mGoodsFormatTwoAdapter = new GoodsFormatTwoAdapter(mContext, specTwoListList);
                tlFormatTwo.setAdapter(mGoodsFormatTwoAdapter);

                return true;
            }
        });
        tlFormatTwo.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                GoodsInfo.SpecListBean.SpecTwoListBean specTwoListBean = specTwoListList.get(position);
                mGoodsFormatTwoAdapter.setClickPosition(position);
                initFormatData(specTwoListBean);
                mGoodsFormatTwoAdapter.notifyDataChanged();
                return true;
            }
        });

        tvAddNum.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (buyNum < totalStock) {
                    buyNum++;
                    tvBuyNum.setText(buyNum + "");
                } else {
                    ToastUtils.show("已经是最大库存！");
                }
            }
        });
        tvReduceNum.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (buyNum > 1) {
                    buyNum--;
                    tvBuyNum.setText(buyNum + "");
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initFormatData(GoodsInfo.SpecListBean.SpecTwoListBean specTwoListBean) {
        if (!specTwoListBean.getSpecTwo().isEmpty()) {
            tvTitle.setText(specTwoListBean.getSpecTwo());
        }
        if (!specTwoListBean.getPrice().isEmpty()) {
            tvPrice.setText("￥" + specTwoListBean.getPrice());
        }
        if (!specTwoListBean.getStock().isEmpty()) {
            tvStock.setText("库存：" + specTwoListBean.getStock());
            totalStock = Integer.parseInt(specTwoListBean.getStock());
        }
        if (!specTwoListBean.getThumbnail().isEmpty()) {
            GlideUtil.with(mContext).displayImage(specTwoListBean.getThumbnail(), ivCover);
        }
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.FROM_BOTTOM)
                .toShow();
    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}