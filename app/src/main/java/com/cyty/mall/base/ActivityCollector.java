package com.cyty.mall.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2021/12/9 15:45
 * @描述 Activity的统一管理工具类
 */
public class ActivityCollector {


    public static List<Activity> mActivities = new ArrayList<Activity>();

    /**
     * 向List中添加一个活动
     *
     * @param activity 活动
     */
    public static void addActivity(Activity activity) {

        mActivities.add(activity);
    }

    /**
     * 从List中移除活动
     *
     * @param activity 活动
     */
    public static void removeActivity(Activity activity) {

        mActivities.remove(activity);
    }

    /**
     * 将List中存储的活动全部销毁掉
     */
    public static void finishAll() {

        for (Activity activity : mActivities) {

            if (!activity.isFinishing()) {

                activity.finish();
            }
        }
    }
    /**
     * 保留指定界面
     *
     * @param cls
     */
    public static void finishAllActivityExcept(Class<?> cls) {

        if (mActivities == null) {
            return;
        }
        Iterator<Activity> iterator = mActivities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (!activity.getClass().equals(cls)) {
                activity.finish();
                iterator.remove();
            }
        }
    }
}

