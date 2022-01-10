package com.cyty.mall.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cyty.mall.factory.MainFragmentFactory;

/**
 * @创建者 misJackLee
 * @创建时间 2021/12/10 9:16
 * @描述 首页Navigation的适配器
 */
public class MainNavigationAdapter extends FragmentPagerAdapter {
    public MainNavigationAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragmentFactory.getFragment(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
