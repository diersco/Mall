package com.cyty.mall.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyty.mall.MainActivity;
import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.contants.MKParameter;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.AppUtils;
import com.cyty.mall.util.MkUtils;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.tv_verification_code)
    TextView tvVerificationCode;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.img_login_wx)
    ImageView imgLoginWx;
    @BindView(R.id.iv_check)
    ImageView ivCheck;
    @BindView(R.id.layout_policy_protocol)
    LinearLayout layoutPolicyProtocol;
    //用于判断验证码样式
    private boolean isClickCode = true;
    private boolean startThread = true;
    //是否点击协议
    private boolean isCheck = false;
    /**
     * 倒计时最大时间
     */
    private final static int MAX_TIME = 60;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initData() {
        if (!MkUtils.decodeString(MKParameter.TOKEN).isEmpty())
            MainActivity.startActivity(mContext);
    }


    @OnClick(R.id.tv_verification_code)
    public void onTvVerificationCodeClicked() {
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
        sendSmsCode(phoneNum);
    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(mIntent);
    }

    @OnClick(R.id.tv_login)
    public void onTvLoginClicked() {
        if (!isCheck) {
            ToastUtils.show("您必须同意/勾选-用户用户协议和隐私政策，才可进行登录。");
            return;
        }
        String phoneNum = etPhoneNum.getText().toString().trim();
        String code = etVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtils.show("请输入手机号");
            return;
        }
        if (!AppUtils.isPhoneNumber(phoneNum)) {
            ToastUtils.show("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.show("请输入验证码");
            return;
        }
        smsLogin(phoneNum, code);
    }

    @OnClick(R.id.img_login_wx)
    public void onImgLoginWxClicked() {
    }

    @OnClick(R.id.layout_policy_protocol)
    public void onLayoutPolicyProtocolClicked() {
        if (isCheck) {
            ivCheck.setImageResource(R.drawable.ic_selected);
            isCheck = !isCheck;
        } else {
            showProtocolDialog();
        }
    }


    /**
     * 验证码登录
     *
     * @param phoneNum
     * @param code
     */
    private void smsLogin(String phoneNum, String code) {
        HttpManager.getInstance().smsLogin(phoneNum, code,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.SmsLoginResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.SmsLoginResponse data) {
                        if (result) {
                            //请求成功后，保存token
                            MkUtils.encode(MKParameter.TOKEN, data.msg);
                            MkUtils.encode(MKParameter.USER_ID, data.data.getId()+"");
                            MainActivity.startActivity(mContext);
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    /**
     * 获取验证码
     *
     * @param phoneNum 电话号码
     */
    private void sendSmsCode(String phoneNum) {
        HttpManager.getInstance().sendSmsCode(phoneNum,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.SendSmsCodeResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.SendSmsCodeResponse data) {
                        if (result) {
                            ToastUtils.show("验证码发送成功！");
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
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 0) {
                isClickCode = true;
                tvVerificationCode.setBackgroundResource(R.drawable.shape_txt_verification_code);
                tvVerificationCode.setTextColor(0xff874BA4);
                tvVerificationCode.setText("获取验证码");
            } else {
                tvVerificationCode.setBackgroundResource(R.drawable.shape_txt_verification_countdown);
                tvVerificationCode.setTextColor(getResources().getColor(R.color.white));
                tvVerificationCode.setText(msg.arg1 + "S");
            }
        }
    };

    /**
     * 协议的弹窗
     */
    private void showProtocolDialog() {
        final View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_protocol, null);
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) inflate.findViewById(R.id.tv_content);
        SpannableString spanString = new SpannableString("《服务协议》和《隐私政策》");
        spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#1d74b6")), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                //点击的响应事件
            }
        }, 0, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_content.setText("服务协议和隐私政策请你务必审慎阅读、充分理解“服务协议”和“隐私政策”各条款，包括但不限于：为了向你提供即时通讯、内容推送等服务，需要收集你的设备信息、操作日志等个人信息。你可阅读");
        tv_content.append(spanString);
        tv_content.append("了解详细信息。如你同意，请点击“同意”开始接受我们的服务。");
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
        final Dialog dialog = new AlertDialog
                .Builder(mContext)
                .setView(inflate)
                .show();
        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivCheck.setImageResource(R.drawable.ic_not_selected);
                isCheck = false;
                dialog.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivCheck.setImageResource(R.drawable.ic_selected);
                isCheck = true;
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startThread = false;
    }

    @Override
    protected void setStatusBar() {

        StatusBarUtil.setTransparent(this);
    }
}