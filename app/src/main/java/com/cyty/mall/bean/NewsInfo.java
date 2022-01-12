package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsInfo implements Parcelable {

    /**
     * id : 4
     * newsType : 1
     * newsTitle : 欢迎来到APP商城
     * newsContent : 欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城欢迎来到APP商城
     * receiver : 0
     * newsRead : 1
     * deletes : 1
     */

    private int id;
    private int newsType;
    private String newsTitle;
    private String newsContent;
    private int receiver;
    private int newsRead;
    private int deletes;

    protected NewsInfo(Parcel in) {
        id = in.readInt();
        newsType = in.readInt();
        newsTitle = in.readString();
        newsContent = in.readString();
        receiver = in.readInt();
        newsRead = in.readInt();
        deletes = in.readInt();
    }

    public static final Creator<NewsInfo> CREATOR = new Creator<NewsInfo>() {
        @Override
        public NewsInfo createFromParcel(Parcel in) {
            return new NewsInfo(in);
        }

        @Override
        public NewsInfo[] newArray(int size) {
            return new NewsInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public int getNewsRead() {
        return newsRead;
    }

    public void setNewsRead(int newsRead) {
        this.newsRead = newsRead;
    }

    public int getDeletes() {
        return deletes;
    }

    public void setDeletes(int deletes) {
        this.deletes = deletes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(newsType);
        dest.writeString(newsTitle);
        dest.writeString(newsContent);
        dest.writeInt(receiver);
        dest.writeInt(newsRead);
        dest.writeInt(deletes);
    }
}
