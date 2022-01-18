package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/18 16:47
 * @描述
 */

public class HomeDataInfo implements Parcelable {
    /**
     * posterList : [{"goodsId":0,"id":3,"resourceLink":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201141804423822405.jpeg","sort":0},{"goodsId":0,"id":4,"resourceLink":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201141805031172397.jpg","sort":1}]
     * bigPictureList : [{"goodsId":0,"id":1,"resourceLink":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201141802289060075.jpg","sort":0},{"goodsId":0,"id":2,"resourceLink":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201141803574231003.jpg","sort":1}]
     * video : {"cover":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201151009209691291.jpg","id":5,"resourceLink":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201151448467590901.mp4"}
     */

    private VideoBean video;
    private List<PosterListBean> posterList;
    private List<BigPictureListBean> bigPictureList;

    protected HomeDataInfo(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HomeDataInfo> CREATOR = new Creator<HomeDataInfo>() {
        @Override
        public HomeDataInfo createFromParcel(Parcel in) {
            return new HomeDataInfo(in);
        }

        @Override
        public HomeDataInfo[] newArray(int size) {
            return new HomeDataInfo[size];
        }
    };

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public List<PosterListBean> getPosterList() {
        return posterList;
    }

    public void setPosterList(List<PosterListBean> posterList) {
        this.posterList = posterList;
    }

    public List<BigPictureListBean> getBigPictureList() {
        return bigPictureList;
    }

    public void setBigPictureList(List<BigPictureListBean> bigPictureList) {
        this.bigPictureList = bigPictureList;
    }

    public static class VideoBean {
        /**
         * cover : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201151009209691291.jpg
         * id : 5
         * resourceLink : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201151448467590901.mp4
         */

        private String cover;
        private int id;
        private String resourceLink;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
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
    }

    public static class PosterListBean {
        /**
         * goodsId : 0
         * id : 3
         * resourceLink : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201141804423822405.jpeg
         * sort : 0
         */

        private int goodsId;
        private int id;
        private String resourceLink;
        private int sort;

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
    }

    public static class BigPictureListBean {
        /**
         * goodsId : 0
         * id : 1
         * resourceLink : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201141802289060075.jpg
         * sort : 0
         */

        private int goodsId;
        private int id;
        private String resourceLink;
        private int sort;

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
    }
}
