package com.cyty.mall.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @创建者 misJackLee
 * @创建时间 2020/6/28 15:06
 * @描述
 */
public class CommPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<? extends Fragment> items;
    private String[] mTitles;

    public CommPagerAdapter(FragmentManager fm, ArrayList<? extends Fragment> items, String[] mTitles) {
        super(fm);
        this.items = items;
        this.mTitles = mTitles;
    }

    @Override
    public int getCount() {
        return items.size() == 0 ? 0 : items.size();
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }


    // 动态设置我们标题的方法
    public void setPageTitle(int position, String title) {
        if (position >= 0 && position < mTitles.length) {
            mTitles[position] = title;
            notifyDataSetChanged();
        }
    }

}