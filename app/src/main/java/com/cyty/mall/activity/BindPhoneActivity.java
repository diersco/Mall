package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cyty.mall.MainActivity;
import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.contants.MKParameter;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.AppUtils;
import com.cyty.mall.util.MkUtils;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 绑定电话号码
 */
public class BindPhoneActivity extends BaseActivity {


    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_verification_code)
    TextView tvVerificationCode;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    //用于判断验证码样式
    private boolean isClickCode = true;
    private boolean startThread = true;
    private String headPortrait;
    private String unionId;
    private String weChatNickname;
    /**
     * 倒计时最大时间
     */
    private final static int MAX_TIME = 60;

    @Override
    protected void onNetReload(View v) {

    }

    public static void startActivity(Context mContext, String headPortrait, String unionId, String weChatNickname) {
        Intent mIntent = new Intent(mContext, BindPhoneActivity.class);
        mIntent.putExtra("headPortrait", headPortrait);
        mIntent.putExtra("unionId", unionId);
        mIntent.putExtra("weChatNickname", weChatNickname);
        mContext.startActivity(mIntent);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initView() {
        headPortrait = getIntent().getStringExtra("headPortrait");
        unionId = getIntent().getStringExtra("unionId");
        weChatNickname = getIntent().getStringExtra("weChatNickname");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolBar() {

    }


    @OnClick({R.id.tv_verification_code, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_verification_code:
                if (!isClickCode) {
                    return;
                }
                String phoneNum = etPhoneNum.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNum)) {
                    ToastUtils.show("请输入手机号");
                    return;
                }

                if (!AppUtils.isPhoneNumber(phoneNum)) {
                    ToastUtils.show("请输入正确的手机号");
                    return;
                }
                startTimeCode();
                sendCode(phoneNum);
                break;
            case R.id.tv_sure:
                String cellPhoneNumber = etPhoneNum.getText().toString().trim();
                if (TextUtils.isEmpty(cellPhoneNumber)) {
                    ToastUtils.show("请输入手机号");
                    return;
                }

                if (!AppUtils.isPhoneNumber(cellPhoneNumber)) {
                    ToastUtils.show("请输入正确的手机号");
                    return;
                }
                String code = etCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.show("请输入验证码");
                    return;
                }
                bindMobileNumber(cellPhoneNumber, code, headPortrait, unionId, weChatNickname);
                break;
        }
    }

    /**
     * 获取绑定验证码
     *
     * @param phoneNum 电话号码
     */
    private void sendCode(String phoneNum) {
        HttpManager.getInstance().sendCode(phoneNum,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.SendCodeResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.SendCodeResponse data) {
                        if (result) {
                            ToastUtils.show("验证码发送成功！");
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }


    /**
     * 绑定手机号
     *
     * @param cellPhoneNumber 电话号码
     * @param code            验证码
     * @param headPortrait    头像
     * @param unionId         unionId
     * @param wechatNickname  昵称
     */
    private void bindMobileNumber(String cellPhoneNumber, String code, String headPortrait, String unionId, String wechatNickname) {
        HttpManager.getInstance().bindMobileNumber(cellPhoneNumber, code, headPortrait, unionId, wechatNickname,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.bindMobileNumberResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.bindMobileNumberResponse data) {
                        if (result) {
                            ToastUtils.show("绑定成功！");
                            //请求成功后，保存token
                            MkUtils.encode(MKParameter.TOKEN, data.data);
                            MainActivity.startActivity(mContext);
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    private void startTimeCode() {
        if (isClickCode) {
            isClickCode = false;
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        if (startThread) {
                            for (int i = MAX_TIME; i >= 0; i--) {
                                Thread.sleep(1000);
                                Message msg = new Message();
                                msg.arg1 = i;
                                mHandler.sendMessage(msg);
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }.start();
        }
    }

    Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 0) {
                isClickCode = true;
                tvVerificationCode.setText("获取验证码");
            } else {
                tvVerificationCode.setText(msg.arg1 + "S");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startThread = false;
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }
}