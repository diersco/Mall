package com.cyty.mall.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyty.mall.R;
import com.cyty.mall.adapter.CommonProblemAdapter;
import com.cyty.mall.adapter.OrderDetailAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.ProblemInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 联系客户
 */
public class ContactCustomerServiceActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private List<ProblemInfo> problemInfoList = new ArrayList<>();
    private CommonProblemAdapter mAdapter;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_contact_customer_service;
    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, ContactCustomerServiceActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("联系客服");
    }

    @Override
    protected void initData() {
        getProblemList();
    }

    /**
     * 获取首页数据
     */
    private void getProblemList() {
        HttpManager.getInstance().getProblemList(
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.getProblemListResponse>() {
                    @Override
                    public void onResponse(boolean result, int total, String message, HttpResponse.getProblemListResponse data) {
                        if (result) {
                            problemInfoList = data.data;
                            tvPhoneNum.setText(data.customerService);
                            initAdapter();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    private void initAdapter() {
        mAdapter = new CommonProblemAdapter(problemInfoList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
    }

    @OnClick(R.id.layout_phone)
    public void onViewClicked() {

        // 权限
        PermissionX.init(ContactCustomerServiceActivity.this)
                .permissions(Manifest.permission.CALL_PHONE)
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
                    callPhone("13326893305");
                } else {
                    ToastUtils.show("你已拒绝相拨打电话的权限！");
                }

            }
        });
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }
}