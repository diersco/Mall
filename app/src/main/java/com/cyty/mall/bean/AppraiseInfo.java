package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AppraiseInfo implements Parcelable {

    /**
     * id : 2
     * userId : 2
     * nickname : 18380438130
     * headPortrait : null
     * goodsId : 1
     * comment : 132123123
     * commentPicture : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201121510353873193.jpeg,https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201121510353599466.jpeg,https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201121510354376596.jpeg,https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201121510354264531.jpeg,https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201121510354022353.jpeg
     * reply : 回复
     * createTime : 2022-01-12 15:10:50
     * updateTime : null
     */

    private int id;
    private int userId;
    private String nickname;
    private String headPortrait;
    private int goodsId;
    private String comment;
    private String commentPicture;
    private String reply;
    private String createTime;
    private String updateTime;

    protected AppraiseInfo(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        nickname = in.readString();
        headPortrait = in.readString();
        goodsId = in.readInt();
        comment = in.readString();
        commentPicture = in.readString();
        reply = in.readString();
        createTime = in.readString();
        updateTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(nickname);
        dest.writeString(headPortrait);
        dest.writeInt(goodsId);
        dest.writeString(comment);
        dest.writeString(commentPicture);
        dest.writeString(reply);
        dest.writeString(createTime);
        dest.writeString(updateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppraiseInfo> CREATOR = new Creator<AppraiseInfo>() {
        @Override
        public AppraiseInfo createFromParcel(Parcel in) {
            return new AppraiseInfo(in);
        }

        @Override
        public AppraiseInfo[] newArray(int size) {
            return new AppraiseInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentPicture() {
        return commentPicture;
    }

    public void setCommentPicture(String commentPicture) {
        this.commentPicture = commentPicture;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
