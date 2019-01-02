package com.softcodeinfotech.locationupdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("Information")
    @Expose
    private Information information;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }
    public class Information {

        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("deviceid")
        @Expose
        private String deviceid;
        @SerializedName("lati")
        @Expose
        private String lati;
        @SerializedName("longi")
        @Expose
        private String longi;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
        }

        public String getLati() {
            return lati;
        }

        public void setLati(String lati) {
            this.lati = lati;
        }

        public String getLongi() {
            return longi;
        }

        public void setLongi(String longi) {
            this.longi = longi;
        }

    }
}
