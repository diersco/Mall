package com.cyty.mall.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.cyty.mall.R;
import com.cyty.mall.adapter.PhotoViewAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.view.PhotoViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PhotoViewActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.photo_viewPager)
    PhotoViewPager photoViewPager;


    private int currentPosition;
    private PhotoViewAdapter adapter;
    private List<String> imageList;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_photo_view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("currentPosition", 0);
        String stringExtra = getIntent().getStringExtra("imageList");
        imageList = new Gson().fromJson(stringExtra, new TypeToken<List<String>>() {
        }.getType());
        adapter = new PhotoViewAdapter(imageList, this);
        photoViewPager.setAdapter(adapter);
        photoViewPager.setCurrentItem(currentPosition, false);
        tvTitle.setText(currentPosition + 1 + "/" + imageList.size());
        photoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvTitle.setText(position + 1 + "/" + imageList.size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initToolBar() {

    }
    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, Color.BLACK, 0);
    }
}