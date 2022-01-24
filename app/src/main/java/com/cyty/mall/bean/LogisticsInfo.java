package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/24 9:34
 * @描述
 */
public class LogisticsInfo implements Parcelable {


    /**
     * message : ok
     * nu : YT6247854916511
     * ischeck : 1
     * com : yuantong
     * status : 200
     * data : [{"time":"2022-01-13 18:52:32","context":"客户签收人: 已签收，签收人凭取货码签收。 已签收 感谢使用圆通速递，期待再次为您服务 如有疑问请联系：18521178673，投诉电话：13408006573。疫情期间圆通每天对网点多次消毒，快递小哥每天测量体温，佩戴口罩","ftime":"2022-01-13 18:52:32","areaCode":null,"areaName":null,"status":"投柜或站签收","location":"","areaCenter":null,"areaPinYin":null,"statusCode":"304"},{"time":"2022-01-13 17:16:34","context":"快件已由成都双流区红树湾二期店菜鸟驿站代收，请及时取件，如有疑问请联系17311222077","ftime":"2022-01-13 17:16:34","areaCode":"CN510116000000","areaName":"四川,成都市,双流区","status":"投柜或驿站","location":"","areaCenter":"103.951908,30.333027","areaPinYin":"shuang liu qu","statusCode":"501"},{"time":"2022-01-13 13:47:04","context":"【四川省成都市高新区中和一部公司 】 派件中 派件人: 王东平 电话 18521178673 。 圆通快递小哥每天已测体温，请放心收寄快递 如有疑问，请联系：18521172442","ftime":"2022-01-13 13:47:04","areaCode":"CN510116004000","areaName":"四川,成都市,双流区,中和","status":"派件","location":"","areaCenter":"104.092459,30.558232","areaPinYin":"zhong he jie dao","statusCode":"5"},{"time":"2022-01-13 13:31:23","context":"【四川省成都市高新区中和一部公司】 已收入","ftime":"2022-01-13 13:31:23","areaCode":"CN510116004000","areaName":"四川,成都市,双流区,中和","status":"在途","location":"","areaCenter":"104.092459,30.558232","areaPinYin":"zhong he jie dao","statusCode":"0"},{"time":"2022-01-13 09:59:45","context":"【成都转运中心公司】 已打包","ftime":"2022-01-13 09:59:45","areaCode":"CN510100000000","areaName":"四川,成都市","status":"干线","location":"","areaCenter":"104.066541,30.572269","areaPinYin":"cheng du shi","statusCode":"1002"},{"time":"2022-01-13 09:55:21","context":"【成都转运中心】 已发出","ftime":"2022-01-13 09:55:21","areaCode":"CN510100000000","areaName":"四川,成都市","status":"干线","location":"","areaCenter":"104.066541,30.572269","areaPinYin":"cheng du shi","statusCode":"1002"},{"time":"2022-01-13 09:05:21","context":"【成都转运中心公司】 已收入","ftime":"2022-01-13 09:05:21","areaCode":"CN510100000000","areaName":"四川,成都市","status":"干线","location":"","areaCenter":"104.066541,30.572269","areaPinYin":"cheng du shi","statusCode":"1002"},{"time":"2022-01-11 23:34:51","context":"【深圳转运中心】 已发出 下一站 【成都转运中心公司】","ftime":"2022-01-11 23:34:51","areaCode":"CN440300000000","areaName":"广东,深圳市","status":"干线","location":"","areaCenter":"114.057868,22.543099","areaPinYin":"shen zhen shi","statusCode":"1002"},{"time":"2022-01-11 23:22:33","context":"【深圳转运中心公司】 已收入","ftime":"2022-01-11 23:22:33","areaCode":"CN440300000000","areaName":"广东,深圳市","status":"干线","location":"","areaCenter":"114.057868,22.543099","areaPinYin":"shen zhen shi","statusCode":"1002"},{"time":"2022-01-11 01:06:02","context":"【广东省深圳市坂田】 已发出 下一站 【深圳转运中心公司】","ftime":"2022-01-11 01:06:02","areaCode":"CN440307013000","areaName":"广东,深圳市,龙岗区,坂田","status":"干线","location":"","areaCenter":"114.083514,22.650441","areaPinYin":"ban tian jie dao","statusCode":"1002"},{"time":"2022-01-11 00:31:24","context":"【广东省深圳市坂田公司】 已打包","ftime":"2022-01-11 00:31:24","areaCode":"CN440307013000","areaName":"广东,深圳市,龙岗区,坂田","status":"在途","location":"","areaCenter":"114.083514,22.650441","areaPinYin":"ban tian jie dao","statusCode":"0"},{"time":"2022-01-10 14:25:24","context":"【广东省惠州市公司】 已揽收 取件人: 武身平 (13886997588)","ftime":"2022-01-10 14:25:24","areaCode":"CN441300000000","areaName":"广东,惠州市","status":"揽收","location":"","areaCenter":"114.416196,23.111847","areaPinYin":"hui zhou shi","statusCode":"1"}]
     * state : 304
     * condition : F00
     * routeInfo : {"from":{"number":"CN441300000000","name":"广东,惠州市"},"cur":{"number":"CN510116000000","name":"四川,成都市,双流区"},"to":{"number":"CN510116000000","name":"四川,成都市,双流区"}}
     * isLoop : false
     */

    private String message;
    private String nu;
    private String ischeck;
    private String com;
    private String status;
    private String state;
    private String condition;
    private RouteInfoBean routeInfo;
    private boolean isLoop;
    private List<DataBean> data;

    protected LogisticsInfo(Parcel in) {
        message = in.readString();
        nu = in.readString();
        ischeck = in.readString();
        com = in.readString();
        status = in.readString();
        state = in.readString();
        condition = in.readString();
        isLoop = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(nu);
        dest.writeString(ischeck);
        dest.writeString(com);
        dest.writeString(status);
        dest.writeString(state);
        dest.writeString(condition);
        dest.writeByte((byte) (isLoop ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LogisticsInfo> CREATOR = new Creator<LogisticsInfo>() {
        @Override
        public LogisticsInfo createFromParcel(Parcel in) {
            return new LogisticsInfo(in);
        }

        @Override
        public LogisticsInfo[] newArray(int size) {
            return new LogisticsInfo[size];
        }
    };

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public RouteInfoBean getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(RouteInfoBean routeInfo) {
        this.routeInfo = routeInfo;
    }

    public boolean isIsLoop() {
        return isLoop;
    }

    public void setIsLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class RouteInfoBean {
        /**
         * from : {"number":"CN441300000000","name":"广东,惠州市"}
         * cur : {"number":"CN510116000000","name":"四川,成都市,双流区"}
         * to : {"number":"CN510116000000","name":"四川,成都市,双流区"}
         */

        private FromBean from;
        private CurBean cur;
        private ToBean to;

        public FromBean getFrom() {
            return from;
        }

        public void setFrom(FromBean from) {
            this.from = from;
        }

        public CurBean getCur() {
            return cur;
        }

        public void setCur(CurBean cur) {
            this.cur = cur;
        }

        public ToBean getTo() {
            return to;
        }

        public void setTo(ToBean to) {
            this.to = to;
        }

        public static class FromBean {
            /**
             * number : CN441300000000
             * name : 广东,惠州市
             */

            private String number;
            private String name;

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class CurBean {
            /**
             * number : CN510116000000
             * name : 四川,成都市,双流区
             */

            private String number;
            private String name;

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ToBean {
            /**
             * number : CN510116000000
             * name : 四川,成都市,双流区
             */

            private String number;
            private String name;

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class DataBean {
        /**
         * time : 2022-01-13 18:52:32
         * context : 客户签收人: 已签收，签收人凭取货码签收。 已签收 感谢使用圆通速递，期待再次为您服务 如有疑问请联系：18521178673，投诉电话：13408006573。疫情期间圆通每天对网点多次消毒，快递小哥每天测量体温，佩戴口罩
         * ftime : 2022-01-13 18:52:32
         * areaCode : null
         * areaName : null
         * status : 投柜或站签收
         * location :
         * areaCenter : null
         * areaPinYin : null
         * statusCode : 304
         */

        private String time;
        private String context;
        private String ftime;
        private Object areaCode;
        private Object areaName;
        private String status;
        private String location;
        private Object areaCenter;
        private Object areaPinYin;
        private String statusCode;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public Object getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(Object areaCode) {
            this.areaCode = areaCode;
        }

        public Object getAreaName() {
            return areaName;
        }

        public void setAreaName(Object areaName) {
            this.areaName = areaName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Object getAreaCenter() {
            return areaCenter;
        }

        public void setAreaCenter(Object areaCenter) {
            this.areaCenter = areaCenter;
        }

        public Object getAreaPinYin() {
            return areaPinYin;
        }

        public void setAreaPinYin(Object areaPinYin) {
            this.areaPinYin = areaPinYin;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }
    }
}
