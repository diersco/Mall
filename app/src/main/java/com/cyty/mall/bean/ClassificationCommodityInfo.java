package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/4 15:23
 * @描述
 */

public class ClassificationCommodityInfo implements Parcelable {
    private List<ClassificationCommodity> classificationCommodityList;

    protected ClassificationCommodityInfo(Parcel in) {
        classificationCommodityList = in.createTypedArrayList(ClassificationCommodity.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(classificationCommodityList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassificationCommodityInfo> CREATOR = new Creator<ClassificationCommodityInfo>() {
        @Override
        public ClassificationCommodityInfo createFromParcel(Parcel in) {
            return new ClassificationCommodityInfo(in);
        }

        @Override
        public ClassificationCommodityInfo[] newArray(int size) {
            return new ClassificationCommodityInfo[size];
        }
    };

    public List<ClassificationCommodity> getClassificationCommodityList() {
        return classificationCommodityList;
    }

    public void setClassificationCommodityList(List<ClassificationCommodity> classificationCommodityList) {
        this.classificationCommodityList = classificationCommodityList;
    }
}
