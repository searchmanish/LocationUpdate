package com.softcodeinfotech.locationupdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SigninResponse {

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

    public SigninResponse withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SigninResponse withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public SigninResponse withInformation(Information information) {
        this.information = information;
        return this;
    }


    public class Information {

        @SerializedName("employee_id")
        @Expose
        private Integer employeeId;
        @SerializedName("employee_name")
        @Expose
        private String employeeName;
        @SerializedName("employee_email")
        @Expose
        private String employeeEmail;
        @SerializedName("employee_password")
        @Expose
        private String employeePassword;

        public Integer getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Integer employeeId) {
            this.employeeId = employeeId;
        }

        public Information withEmployeeId(Integer employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public Information withEmployeeName(String employeeName) {
            this.employeeName = employeeName;
            return this;
        }

        public String getEmployeeEmail() {
            return employeeEmail;
        }

        public void setEmployeeEmail(String employeeEmail) {
            this.employeeEmail = employeeEmail;
        }

        public Information withEmployeeEmail(String employeeEmail) {
            this.employeeEmail = employeeEmail;
            return this;
        }

        public String getEmployeePassword() {
            return employeePassword;
        }

        public void setEmployeePassword(String employeePassword) {
            this.employeePassword = employeePassword;
        }

        public Information withEmployeePassword(String employeePassword) {
            this.employeePassword = employeePassword;
            return this;
        }

    }
}
