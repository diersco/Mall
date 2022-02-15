package com.cyty.mall.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyty.mall.R;
import com.cyty.mall.activity.ConfirmOrderActivity;
import com.cyty.mall.activity.IntegralConfirmOrderActivity;
import com.cyty.mall.activity.SeckillConfirmOrderActivity;
import com.cyty.mall.adapter.GoodsFormatAdapter;
import com.cyty.mall.adapter.GoodsFormatTwoAdapter;
import com.cyty.mall.bean.GoodsInfo;
import com.cyty.mall.event.RefreshUniversalListEvent;
import com.cyty.mall.event.RefreshUserInfoEvent;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.GlideUtil;
import com.hjq.toast.ToastUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

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
    LinearLayout layoutNum;


    private Context mContext;
    private GoodsInfo mGoodsInfo;
    private GoodsFormatAdapter mGoodsFormatAdapter;
    private GoodsFormatTwoAdapter mGoodsFormatTwoAdapter;
    private List<GoodsInfo.SpecListBean> specListBeanList = new ArrayList<>();
    private List<GoodsInfo.SpecListBean.SpecTwoListBean> specTwoListList = new ArrayList<>();
    //选择的购买数量
    private int buyNum = 1;
    // 判断是立即购买还是加入购物车或收藏  1 立即购买 2 加入购物车 3 收藏 4 积分立即兑换 5 秒杀立即购买
    private int type;
    private int totalStock;
    //商品规格id
    private String formatId;
    //规格编号字符串
    private String ids;
    private GoodsInfo.SpecListBean.SpecTwoListBean listBean;
    private String title;
    private String spec;

    public GoodsFormatPopup(Context context, GoodsInfo goodsInfo, int type) {
        super(context);
        this.mContext = context;
        this.mGoodsInfo = goodsInfo;
        this.type = type;
        setContentView(R.layout.layout_goods_popup);
        setPopupGravity(Gravity.BOTTOM);
        initGoodsData(mGoodsInfo);

    }


    private void initGoodsData(GoodsInfo mGoodsInfo) {
        layoutNum = findViewById(R.id.layout_num);
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
        title = mGoodsInfo.getTitle();
        mGoodsFormatAdapter = new GoodsFormatAdapter(mContext, specListBeanList);
        tlFormatOne.setAdapter(mGoodsFormatAdapter);
        if (type == 4) {
            layoutNum.setVisibility(View.GONE);
        }
        specTwoListList = specListBeanList.get(0).getSpecTwoList();
        listBean = specListBeanList.get(0).getSpecTwoList().get(0);
        spec = specListBeanList.get(0).getSpecOne() + specListBeanList.get(0).getSpecTwoList().get(0).getSpecTwo();
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
                listBean = specTwoListList.get(position);
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
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) {
                    if (buyNum < totalStock) {
                        ids = formatId + "|" + buyNum;
                        ConfirmOrderActivity.startActivity(mContext, ids);
                        dismiss();
                    } else {
                        dismiss();
                        ToastUtils.show("库存不足！");
                    }

                } else if (type == 2) {
                    addShoppingCart();
                    dismiss();
                } else if (type == 3) {
                    collections();
                    dismiss();
                } else if (type == 4) {
                    if (totalStock > 0) {
                        IntegralConfirmOrderActivity.startActivity(mContext, title, listBean.getThumbnail(), spec, listBean.getIntegral(), formatId);
                        dismiss();
                    } else {
                        dismiss();
                        ToastUtils.show("库存不足！");
                    }

                } else if (type == 5) {
                    if (buyNum < totalStock) {
                        ids = formatId + "|" + buyNum;
                        SeckillConfirmOrderActivity.startActivity(mContext, ids);
                        dismiss();
                    } else {
                        dismiss();
                        ToastUtils.show("库存不足！");
                    }
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initFormatData(GoodsInfo.SpecListBean.SpecTwoListBean specTwoListBean) {
        formatId = specTwoListBean.getId();
        if (!specTwoListBean.getSpecTwo().isEmpty()) {
            tvTitle.setText(specTwoListBean.getSpecTwo());
        }
        if (type == 4) {
            if (specTwoListBean.getIntegral() > 0) {
                tvPrice.setText(specTwoListBean.getIntegral() + "积分");
            }
        } else {
            if (!specTwoListBean.getPrice().isEmpty()) {
                tvPrice.setText("￥" + specTwoListBean.getPrice());
            }
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

    /**
     * 收藏
     */
    private void collections() {
        HttpManager.getInstance().collections(formatId,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.collectionResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.collectionResponse data) {
                        if (result) {
                            ToastUtils.show("收藏成功！");
                            EventBus.getDefault().post(new RefreshUniversalListEvent());
                            EventBus.getDefault().post(new RefreshUserInfoEvent());
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    /**
     * 加入购物车
     */
    private void addShoppingCart() {
        HttpManager.getInstance().addShoppingCart(formatId, buyNum + "",
                new HttpEngine.HttpResponseResultCallback<HttpResponse.addShoppingCartResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.addShoppingCartResponse data) {
                        if (result) {
                            ToastUtils.show("加入成功！");
                            EventBus.getDefault().post(new RefreshUniversalListEvent());
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
