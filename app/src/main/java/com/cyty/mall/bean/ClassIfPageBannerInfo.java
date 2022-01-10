package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/4 14:05
 * @描述
 */

public class ClassIfPageBannerInfo implements Parcelable {

    private List<ClassifPageBannerListBean> classifPageBannerList;

    protected ClassIfPageBannerInfo(Parcel in) {
        classifPageBannerList = in.createTypedArrayList(ClassifPageBannerListBean.CREATOR);
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(classifPageBannerList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassIfPageBannerInfo> CREATOR = new Creator<ClassIfPageBannerInfo>() {
        @Override
        public ClassIfPageBannerInfo createFromParcel(Parcel in) {
            return new ClassIfPageBannerInfo(in);
        }

        @Override
        public ClassIfPageBannerInfo[] newArray(int size) {
            return new ClassIfPageBannerInfo[size];
        }
    };

    public List<ClassifPageBannerListBean> getClassifPageBannerList() {
        return classifPageBannerList;
    }

    public void setClassifPageBannerList(List<ClassifPageBannerListBean> classifPageBannerList) {
        this.classifPageBannerList = classifPageBannerList;
    }

    public static class ClassifPageBannerListBean implements Parcelable{
        /**
         * goodsId : 0
         * id : 2
         * resourceLink : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112281005223118672.jpg
         * sort : 1
         */

        private int goodsId;
        private int id;
        private String resourceLink;
        private int sort;

        protected ClassifPageBannerListBean(Parcel in) {
            goodsId = in.readInt();
            id = in.readInt();
            resourceLink = in.readString();
            sort = in.readInt();
        }

        public static final Creator<ClassifPageBannerListBean> CREATOR = new Creator<ClassifPageBannerListBean>() {
            @Override
            public ClassifPageBannerListBean createFromParcel(Parcel in) {
                return new ClassifPageBannerListBean(in);
            }

            @Override
            public ClassifPageBannerListBean[] newArray(int size) {
                return new ClassifPageBannerListBean[size];
            }
        };

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getResourceLink() {
            return resourceLink;
        }

        public void setResourceLink(String resourceLink) {
            this.resourceLink = resourceLink;
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
            parcel.writeInt(goodsId);
            parcel.writeInt(id);
            parcel.writeString(resourceLink);
            parcel.writeInt(sort);
        }
    }
}
