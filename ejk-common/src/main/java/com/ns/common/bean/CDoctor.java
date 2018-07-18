package com.ns.common.bean;


/**
 * Created by xuezhucao on 2017/11/16.
 */
public class CDoctor {

    private Long thcCOrigId;
    private String thcCOrigName;

    private String thcCDptId;
    private String thcCDptName;

    private String thcCDoctorId;
    private String thcCDoctorName;

    private String thcCToken;

    public Long getOrigId() {
        return thcCOrigId;
    }

    public void setThcCOrigId(Long thcCOrigId) {
        this.thcCOrigId = thcCOrigId;
    }

    public String getOrigName() {
        return thcCOrigName;
    }

    public void setThcCOrigName(String thcCOrigName) {
        this.thcCOrigName = thcCOrigName;
    }

    public String getDptId() {
        return thcCDptId;
    }

    public void setThcCDptId(String thcCDptId) {
        this.thcCDptId = thcCDptId;
    }

    public String getDptName() {
        return thcCDptName;
    }

    public void setThcCDptName(String thcCDptName) {
        this.thcCDptName = thcCDptName;
    }

    public String getDoctorId() {
        return thcCDoctorId;
    }

    public void setThcCDoctorId(String thcCDoctorId) {
        this.thcCDoctorId = thcCDoctorId;
    }

    public String getDoctorName() {
        return thcCDoctorName;
    }

    public void setThcCDoctorName(String thcCDoctorName) {
        this.thcCDoctorName = thcCDoctorName;
    }

    public String getToken() {
        return thcCToken;
    }

    public void setThcCToken(String thcCToken) {
        this.thcCToken = thcCToken;
    }

    @Override
    public String toString() {
        return "CDoctor [thcCOrigId=" + thcCOrigId + ", thcCOrigName=" + thcCOrigName + ", thcCDptId=" + thcCDptId + ", thcCDptName=" + thcCDptName
                + ", thcCDoctorId=" + thcCDoctorId + ", thcCDoctorName=" + thcCDoctorName + ", thcCToken=" + thcCToken + "]";
    }


}