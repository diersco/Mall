package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyty.mall.R;
import com.cyty.mall.adapter.AddImageAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.RefundDetailsInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.GlideUtil;
import com.cyty.mall.util.StringUtils;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 退款详情页面
 */
public class RefundDetailsActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_goods_title)
    TextView tvGoodsTitle;
    @BindView(R.id.tv_format)
    TextView tvFormat;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.layout_bill)
    LinearLayout layoutBill;
    @BindView(R.id.tv_appeal)
    TextView tvAppeal;
    @BindView(R.id.recyclerView_images)
    RecyclerView recyclerViewImages;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_bill)
    TextView tvBill;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    private String orderID;
    private String returnWaybill;
    private int type;
    private RefundDetailsInfo refundDetailsInfo;
    private AddImageAdapter addImageAdapter;
    private List<String> imgList = new ArrayList<>();

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        if (type == 1) {
            tvTitle.setText("售后详情");
        } else {
            tvTitle.setText("反馈");
        }

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_refund_details;
    }

    public static void startActivity(Context mContext, String orderID, int type) {
        Intent mIntent = new Intent(mContext, RefundDetailsActivity.class);
        mIntent.putExtra(Constant.INTENT_ID, orderID);
        mIntent.putExtra(Constant.INTENT_TYPE, type);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {
        orderID = getIntent().getStringExtra(Constant.INTENT_ID);
        type = getIntent().getIntExtra(Constant.INTENT_TYPE, 0);
    }

    @Override
    protected void initData() {
        selectAfterSaleById();
    }


    /**
     * 查看售后详情
     */
    private void selectAfterSaleById() {
        HttpManager.getInstance().selectAfterSaleById(orderID,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.selectAfterSaleByIdResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.selectAfterSaleByIdResponse data) {
                        if (result) {
                            refundDetailsInfo = data.data;
                            initAfterSaleData();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void initAfterSaleData() {

        if (!StringUtils.isEmpty(refundDetailsInfo.getGoodsTitle())) {
            tvGoodsTitle.setText(refundDetailsInfo.getGoodsTitle());
        }
        if (!StringUtils.isEmpty(refundDetailsInfo.getSpec())) {
            tvFormat.setText("规格：" + refundDetailsInfo.getSpec());
        }
        if (!StringUtils.isEmpty(refundDetailsInfo.getAppealPicture())) {
            String[] split = refundDetailsInfo.getAppealPicture().split(",");
            imgList = Arrays.asList(split);
            addImageAdapter = new AddImageAdapter(imgList);
            recyclerViewImages.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));
            recyclerViewImages.setItemAnimator(new DefaultItemAnimator());
            recyclerViewImages.setAdapter(addImageAdapter);
        }
        GlideUtil.with(mContext).displayImage(refundDetailsInfo.getThumbnail(), ivCover);
        //如果没有这个信息，说明订单已经取消退款
        if (!StringUtils.isEmpty(refundDetailsInfo.getAppeal())) {
            tvAppeal.setText(refundDetailsInfo.getAppeal());
            if (type == 1) {
                if (!StringUtils.isEmpty(refundDetailsInfo.getOrderNumber())) {
                    tvSure.setVisibility(View.VISIBLE);
                    layoutBill.setVisibility(View.VISIBLE);
                    tvFeedback.setVisibility(View.GONE);
                    tvBill.setVisibility(View.GONE);
                } else {
                    tvSure.setVisibility(View.GONE);
                    layoutBill.setVisibility(View.GONE);
                    tvFeedback.setVisibility(View.VISIBLE);
                    tvBill.setVisibility(View.VISIBLE);
                    if (!StringUtils.isEmpty(refundDetailsInfo.getFeedback())) {
                        tvFeedback.setText("客服回复：" + refundDetailsInfo.getFeedback());
                    }
                    if (!StringUtils.isEmpty(refundDetailsInfo.getReturnWaybill())) {
                        tvBill.setText("运单号：" + refundDetailsInfo.getReturnWaybill());
                    }
                }
            } else {
                tvSure.setVisibility(View.GONE);
                layoutBill.setVisibility(View.GONE);
                tvFeedback.setVisibility(View.VISIBLE);
                tvBill.setVisibility(View.VISIBLE);
                if (!StringUtils.isEmpty(refundDetailsInfo.getFeedback())) {
                    tvFeedback.setText("客服回复：" + refundDetailsInfo.getFeedback());
                }
                if (!StringUtils.isEmpty(refundDetailsInfo.getReturnWaybill())) {
                    tvBill.setText("运单号：" + refundDetailsInfo.getReturnWaybill());
                }
            }
        }else {
            tvSure.setVisibility(View.GONE);
            layoutBill.setVisibility(View.GONE);
            tvFeedback.setVisibility(View.GONE);
            tvBill.setVisibility(View.GONE);
        }


    }


    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        returnWaybill = etMessage.getText().toString().trim();
        if (!StringUtils.isEmpty(returnWaybill)) {
            addReturnWaybill(refundDetailsInfo.getId(), returnWaybill);
        } else {
            ToastUtils.show("订单号不能为空");
        }
    }

    /**
     * 取消订单
     */
    private void addReturnWaybill(int id, String ids) {
        HttpManager.getInstance().addReturnWaybill(id, ids,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.addReturnWaybillResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.addReturnWaybillResponse data) {
                        if (result) {
                            ToastUtils.show("取消成功");
                            selectAfterSaleById();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }
    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }
}