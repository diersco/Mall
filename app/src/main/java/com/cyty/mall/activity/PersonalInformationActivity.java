package com.cyty.mall.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.util.DateTools;
import com.cyty.mall.util.FileUtils;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人资料
 */
public class PersonalInformationActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.et_nickName)
    EditText etNickName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.layout_birthday)
    LinearLayout layoutBirthday;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    //生日选择器
    private TimePickerDialog birthdayDialog;
    private long years = 100L * 365 * 1000 * 60 * 60 * 24L;
    private String birthday;
    private static final int REQUEST_CODE_HEAD = 0x123;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("个人资料");
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_personal_information;
    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, PersonalInformationActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        birthdayDialog = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        birthday = DateTools.getFormat(millseconds, "yyyy-MM-dd");
                        tvBirthday.setText(birthday);
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择生日")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setMinMillseconds(System.currentTimeMillis() - years)
                .setMaxMillseconds(System.currentTimeMillis())
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(14)
                .build();
    }


    @OnClick(R.id.iv_head)
    public void onViewClicked() {
        PermissionX.init(PersonalInformationActivity.this)
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
                    Matisse.from(PersonalInformationActivity.this)
                            .choose(MimeType.ofImage())//图片类型
                            .countable(true)//true:选中后显示数字;false:选中后显示对号
                            .maxSelectable(1)//可选的最大数
                            .capture(true)//选择照片时，是否显示拍照
                            .captureStrategy(new CaptureStrategy(true, getApplicationContext().getPackageName() + ".android7.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                            .forResult(REQUEST_CODE_HEAD);
                } else {
                    ToastUtils.show("你已拒绝相应的权限，无法发布动态");
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
                    List<Uri> result2 = Matisse.obtainResult(data);
                    if (result2 != null && result2.size() > 0) {
                        Glide.with(mContext).load(result2.get(0))
                                .into(ivHead);
                        File file = FileUtils.uriToFile(result2.get(0), mContext);
//                        uploadHead(file);
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

    @OnClick({R.id.layout_birthday, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_birthday:
                birthdayDialog.show(getSupportFragmentManager(), "all");
                break;
            case R.id.tv_sure:
                break;
        }
    }
}