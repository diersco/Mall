package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/4 15:21
 * @描述 商品分类
 */
public class ClassificationCommodity implements Parcelable {

    /**
     * id : 2
     * title : 面霜
     * synopsis : 精简护肤
     * sort : 0
     */

    private int id;
    private String title;
    private String synopsis;
    private int sort;

    protected ClassificationCommodity(Parcel in) {
        id = in.readInt();
        title = in.readString();
        synopsis = in.readString();
        sort = in.readInt();
    }

    public static final Creator<ClassificationCommodity> CREATOR = new Creator<ClassificationCommodity>() {
        @Override
        public ClassificationCommodity createFromParcel(Parcel in) {
            return new ClassificationCommodity(in);
        }

        @Override
        public ClassificationCommodity[] newArray(int size) {
            return new ClassificationCommodity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(synopsis);
        parcel.writeInt(sort);
    }
}
