package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ProblemInfo implements Parcelable {

    /**
     * id : 1
     * problemTitle : 1
     * replyContent : 1
     * sort : 0
     * deletes : 1
     */

    private int id;
    private String problemTitle;
    private String replyContent;
    private int sort;
    private int deletes;

    protected ProblemInfo(Parcel in) {
        id = in.readInt();
        problemTitle = in.readString();
        replyContent = in.readString();
        sort = in.readInt();
        deletes = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(problemTitle);
        dest.writeString(replyContent);
        dest.writeInt(sort);
        dest.writeInt(deletes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProblemInfo> CREATOR = new Creator<ProblemInfo>() {
        @Override
        public ProblemInfo createFromParcel(Parcel in) {
            return new ProblemInfo(in);
        }

        @Override
        public ProblemInfo[] newArray(int size) {
            return new ProblemInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getDeletes() {
        return deletes;
    }

    public void setDeletes(int deletes) {
        this.deletes = deletes;
    }
}
