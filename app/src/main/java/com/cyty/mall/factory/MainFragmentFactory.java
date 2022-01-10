package com.cyty.mall.factory;

import androidx.fragment.app.Fragment;

import com.cyty.mall.fragment.CartFragment;
import com.cyty.mall.fragment.ClassificationFragment;
import com.cyty.mall.fragment.FrontPageFragment;
import com.cyty.mall.fragment.MineFragment;

import java.util.HashMap;

/**
 * @创建者 misJackLee
 * @创建时间 2021/12/10 9:07
 * @描述 首页fragment 工厂
 */
public class MainFragmentFactory {

    private static final int FRONT_PAGE = 0; //首页
    private static final int CLASSIFICATION = 1;// 分类
    private static final int CART = 2;// 购物车
    private static final int MINE = 3;//我的


    private static HashMap<Integer, Fragment> mMap = new HashMap();

    public static Fragment getFragment(int index) {
        Fragment fragment = null;
        switch (index) {
            case FRONT_PAGE:
                fragment = new FrontPageFragment();
                break;
            case CLASSIFICATION:
                fragment = new ClassificationFragment();
                break;
            case CART:
                fragment = new CartFragment();
                break;
            case MINE:
                fragment = new MineFragment();
                break;
        }
        return fragment;

    }
}
