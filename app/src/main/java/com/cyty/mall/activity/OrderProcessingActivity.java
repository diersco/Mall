package com.cyty.mall.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.cyty.mall.R;
import com.cyty.mall.adapter.AddImageAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.OrderDetailInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.GlideUtil;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单处理
 */
public class OrderProcessingActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_order_process)
    ImageView ivOrderProcess;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_goods_title)
    TextView tvGoodsTitle;
    @BindView(R.id.tv_format)
    TextView tvFormat;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.recyclerView_images)
    RecyclerView recyclerViewImages;
    private String orderID;
    private int type;
    private OrderDetailInfo.OrderDetailsListBean orderDetailsListBean;
    private AddImageAdapter addImageAdapter;
    private List<String> mSelectList = new ArrayList<>();
    private List<String> mUploadList = new ArrayList<>();
    private static final int REQUEST_CODE_HEAD = 0x123;
    private String appeal;
    private String orderDetailsId;
    private String salesType;

    protected void onNetReload(View v) {

    }

    public static void startActivity(Context mContext, OrderDetailInfo.OrderDetailsListBean orderDetailsListBean, String orderID, int type) {
        Intent mIntent = new Intent(mContext, OrderProcessingActivity.class);
        mIntent.putExtra(Constant.INTENT_DATA, orderDetailsListBean);
        mIntent.putExtra(Constant.INTENT_ID, orderID);
        mIntent.putExtra(Constant.INTENT_TYPE, type);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        switch (type) {
            case 1:
                tvTitle.setText("退货");
                break;
            case 2:
                tvTitle.setText("退款");
                break;
            case 3:
                tvTitle.setText("退货退款");
                break;
            case 4:
                tvTitle.setText("售后");
                break;
        }

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_order_processing;
    }

    @Override
    protected void initView() {
        orderDetailsListBean = (OrderDetailInfo.OrderDetailsListBean) getIntent().getParcelableExtra(Constant.INTENT_DATA);
        orderID = getIntent().getStringExtra(Constant.INTENT_ID);
        type = getIntent().getIntExtra(Constant.INTENT_TYPE, 0);
    }

    @Override
    protected void initData() {
        GlideUtil.with(mContext).displayImage(orderDetailsListBean.getThumbnail(), ivCover);
        tvGoodsTitle.setText(orderDetailsListBean.getGoodsTitle());
        tvFormat.setText(orderDetailsListBean.getSpec());
        tvPrice.setText("￥" + orderDetailsListBean.getPrice());
        tvNum.setText("x" + orderDetailsListBean.getQuantity());
        addImageAdapter = new AddImageAdapter(mSelectList);
        recyclerViewImages.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));
        recyclerViewImages.setItemAnimator(new DefaultItemAnimator());
        recyclerViewImages.setAdapter(addImageAdapter);
        mSelectList.add("");
        addImageAdapter.addChildClickViewIds(R.id.layout_add, R.id.iv_cover);
        addImageAdapter.addChildLongClickViewIds(R.id.iv_cover);
        addImageAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.layout_add) {
                    PermissionX.init(OrderProcessingActivity.this)
                            .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                            .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam() {
                                @Override
                                public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest) {
                                    scope.showRequestReasonDialog(deniedList, "需要您同意以下权限才能正常使用", "确定", "取消");
                                }
                            }).onForwardToSettings(new ForwardToSettingsCallback() {
                        @Override
                        public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                            scope.showForwardToSettingsDialog(deniedList, "您需要去设置中手动开启以下权限", "确定");
                        }
                    }).request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                            if (allGranted) {
                                Matisse.from(OrderProcessingActivity.this)
                                        .choose(MimeType.ofImage())//图片类型
                                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                                        .maxSelectable(9 - mUploadList.size())//可选的最大数
                                        .capture(true)//选择照片时，是否显示拍照
                                        .captureStrategy(new CaptureStrategy(true, getApplicationContext().getPackageName() + ".android7.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                                        .forResult(REQUEST_CODE_HEAD);
                            } else {
                                ToastUtils.show("你已拒绝相应的权限，无法发布动态");
                            }
                        }
                    });
                } else if (view.getId() == R.id.iv_cover) {
                    Intent intent = new Intent(mContext, PhotoViewActivity.class);
                    intent.putExtra("currentPosition", position);
                    intent.putExtra("imageList", new Gson().toJson(mUploadList));
                    mContext.startActivity(intent);
                }

            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_HEAD:
                    List<String> result = Matisse.obtainPathResult(data);
                    if (result != null && result.size() > 0) {
                        if (mSelectList.size() > 0) {
                            mSelectList.remove(mSelectList.size() - 1);
                        }
                        mSelectList.addAll(result);
                        mUploadList.addAll(result);
                        if (mSelectList.size() < 9) {
                            mSelectList.add("");
                        }
                        addImageAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }

    /**
     * 售后
     */
    private void selectMallMember() {
        HttpManager.getInstance().afterSale(appeal, orderDetailsId, salesType, orderID,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.afterSaleResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.afterSaleResponse data) {
                        if (result) {
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }


    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        getHomePageData();
    }

    /**
     * 获取首页数据
     */
    private void getHomePageData() {
        HttpManager.getInstance().uploadImg(mUploadList,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.uploadImgResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.uploadImgResponse data) {
                        if (result) {
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }
}