package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/14 10:50
 * @描述
 */
public class ArticleInfo implements Parcelable {

    /**
     * id : 3
     * articleTitle : 隐私政策
     * classification : 3
     * articleContent : &lt;p&gt;隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策隐私政策&lt;/p&gt;
     * deletes : 1
     */

    private int id;
    private String articleTitle;
    private int classification;
    private String articleContent;
    private int deletes;

    protected ArticleInfo(Parcel in) {
        id = in.readInt();
        articleTitle = in.readString();
        classification = in.readInt();
        articleContent = in.readString();
        deletes = in.readInt();
    }

    public static final Creator<ArticleInfo> CREATOR = new Creator<ArticleInfo>() {
        @Override
        public ArticleInfo createFromParcel(Parcel in) {
            return new ArticleInfo(in);
        }

        @Override
        public ArticleInfo[] newArray(int size) {
            return new ArticleInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(articleTitle);
        parcel.writeInt(classification);
        parcel.writeString(articleContent);
        parcel.writeInt(deletes);
    }
}
