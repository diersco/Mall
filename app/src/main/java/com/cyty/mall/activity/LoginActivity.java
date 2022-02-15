package com.cyty.mall.activity;

import android.annotation.SuppressLint;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
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
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.umverify.UMResultCode;
import com.umeng.umverify.UMVerifyHelper;
import com.umeng.umverify.listener.UMPreLoginResultListener;
import com.umeng.umverify.listener.UMTokenResultListener;
import com.umeng.umverify.model.UMTokenRet;

import java.util.Map;

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
    private String token;
    /**
     * 倒计时最大时间
     */
    private final static int MAX_TIME = 60;
    private UMTokenResultListener mCheckListener;
    private UMTokenResultListener mTokenResultListener;
    private boolean sdkAvailable = true;
    private UMVerifyHelper mPhoneNumberAuthHelper;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        sdkInit();
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initData() {
//        mPhoneNumberAuthHelper.pnsReporterSetLoggerEnable(true);
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
        if (!isCheck) {
            ToastUtils.show("您必须同意/勾选-用户用户协议和隐私政策，才可进行登录。");
            return;
        }
        UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
    }

    @OnClick(R.id.img_login_one_click)
    public void onImgLoginOneClickClicked() {
        if (!isCheck) {
            ToastUtils.show("您必须同意/勾选-用户用户协议和隐私政策，才可进行登录。");
            return;
        }
        if (sdkAvailable) {
            getLoginToken(5000);
        } else {
            ToastUtils.show("一键登录失败，请用其他方式登录！");
        }
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
        @SuppressLint("HandlerLeak")
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
        SpannableString spanUserString = new SpannableString("《用户协议》");
        SpannableString spanPrivacyString = new SpannableString("《隐私政策》");
        spanUserString.setSpan(new ForegroundColorSpan(Color.parseColor("#1d74b6")), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanUserString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                CommonActivity.startActivity(mContext, 2);
                //点击的响应事件
            }
        }, 0, spanUserString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanPrivacyString.setSpan(new ForegroundColorSpan(Color.parseColor("#1d74b6")), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanPrivacyString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                //点击的响应事件
                CommonActivity.startActivity(mContext, 3);
            }
        }, 0, spanPrivacyString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_content.setText("用户协议和隐私政策请你务必审慎阅读、充分理解“用户协议”和“隐私政策”各条款，包括但不限于：为了向你提供即时通讯、内容推送等服务，需要收集你的设备信息、操作日志等个人信息。你可阅读");
        tv_content.append(spanUserString);
        tv_content.append("和");
        tv_content.append(spanPrivacyString);
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

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String uid = data.get("uid");
            String name = data.get("name");
            String iconUrl = data.get("iconurl");
            weChatLogin(iconUrl, uid, name);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(mContext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 微信登录
     */
    private void weChatLogin(String headPortrait, String unionId, String weChatNickname) {
        HttpManager.getInstance().weChatLogin(headPortrait, unionId, weChatNickname,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.weChatLoginResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.weChatLoginResponse data) {
                        if (result) {
                            if (data.bind){
                                //请求成功后，保存token
                                MkUtils.encode(MKParameter.TOKEN, data.data);
                                MainActivity.startActivity(mContext);
                            }else {
                                BindPhoneActivity.startActivity(mContext,headPortrait,unionId,weChatNickname);
                            }
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    /**
     * 一键登录
     */
    private void easyLogin(String token) {
        HttpManager.getInstance().easyLogin(token,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.easyLoginResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.easyLoginResponse data) {
                        if (result) {
                            //请求成功后，保存token
                            MkUtils.encode(MKParameter.TOKEN, data.msg);
                            MainActivity.startActivity(mContext);
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    public void sdkInit() {
        mCheckListener = new UMTokenResultListener() {
            @Override
            public void onTokenSuccess(String s) {
                try {
                    Log.i(TAG, "checkEnvAvailable：" + s);
                    UMTokenRet pTokenRet = UMTokenRet.fromJson(s);
                    if (UMResultCode.CODE_ERROR_ENV_CHECK_SUCCESS.equals(pTokenRet.getCode())) {
                        accelerateLoginPage(5000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTokenFailed(String s) {
                LogUtils.d(s);
                sdkAvailable = false;
            }
        };
        mPhoneNumberAuthHelper = UMVerifyHelper.getInstance(this, mCheckListener);
        mPhoneNumberAuthHelper.setAuthSDKInfo(Constant.WEI_UMENG_APP_ID);
        mPhoneNumberAuthHelper.checkEnvAvailable(UMVerifyHelper.SERVICE_TYPE_LOGIN);
    }

    /**
     * 在不是一进app就需要登录的场景 建议调用此接口 加速拉起一键登录页面
     * 等到用户点击登录的时候 授权页可以秒拉
     * 预取号的成功与否不影响一键登录功能，所以不需要等待预取号的返回。
     *
     * @param timeout
     */
    public void accelerateLoginPage(int timeout) {
        mPhoneNumberAuthHelper.accelerateLoginPage(timeout, new UMPreLoginResultListener() {
            @Override
            public void onTokenSuccess(String s) {
                Log.e(TAG, "预取号成功: " + s);
            }

            @Override
            public void onTokenFailed(String s, String s1) {
                Log.e(TAG, "预取号失败：" + ", " + s1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startThread = false;
    }

    /**
     * 拉起授权页
     *
     * @param timeout 超时时间
     */
    public void getLoginToken(int timeout) {
        mTokenResultListener = new UMTokenResultListener() {
            @Override
            public void onTokenSuccess(String s) {
                UMTokenRet tokenRet = null;
                try {
                    tokenRet = UMTokenRet.fromJson(s);
                    if (UMResultCode.CODE_START_AUTHPAGE_SUCCESS.equals(tokenRet.getCode())) {
                        Log.i(TAG, "唤起授权页成功：" + s);
                    }

                    if (UMResultCode.CODE_GET_TOKEN_SUCCESS.equals(tokenRet.getCode())) {
//                        Log.i(TAG, "获取token成功：" + s);
                        token = tokenRet.getToken();
                        Log.i(TAG, "获取token成功：" + token);
                        easyLogin(token);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onTokenFailed(String s) {
                ToastUtils.show("一键登录失败");
                mPhoneNumberAuthHelper.quitLoginPage();

            }
        };
        mPhoneNumberAuthHelper.setAuthListener(mTokenResultListener);
        mPhoneNumberAuthHelper.getLoginToken(this, timeout);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
    }
}