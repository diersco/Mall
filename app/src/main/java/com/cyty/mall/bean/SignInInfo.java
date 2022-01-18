package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/18 9:58
 * @描述
 */
public class SignInInfo implements Parcelable {

    /**
     * userIntegral : 10000
     * signIn : 2
     * continuity : 1
     * checkPoints : 10000
     */

    private int userIntegral;
    private int signIn;
    private int continuity;
    private int checkPoints;

    protected SignInInfo(Parcel in) {
        userIntegral = in.readInt();
        signIn = in.readInt();
        continuity = in.readInt();
        checkPoints = in.readInt();
    }

    public static final Creator<SignInInfo> CREATOR = new Creator<SignInInfo>() {
        @Override
        public SignInInfo createFromParcel(Parcel in) {
            return new SignInInfo(in);
        }

        @Override
        public SignInInfo[] newArray(int size) {
            return new SignInInfo[size];
        }
    };

    public int getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(int userIntegral) {
        this.userIntegral = userIntegral;
    }

    public int getSignIn() {
        return signIn;
    }

    public void setSignIn(int signIn) {
        this.signIn = signIn;
    }

    public int getContinuity() {
        return continuity;
    }

    public void setContinuity(int continuity) {
        this.continuity = continuity;
    }

    public int getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(int checkPoints) {
        this.checkPoints = checkPoints;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userIntegral);
        parcel.writeInt(signIn);
        parcel.writeInt(continuity);
        parcel.writeInt(checkPoints);
    }
}
