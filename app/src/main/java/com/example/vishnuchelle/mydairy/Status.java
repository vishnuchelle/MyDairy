package com.example.vishnuchelle.mydairy;

/**
 * Created by Vishnu Chelle on 3/17/2015.
 */
public class Status {

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String date;
    private String status;
    private String userName;

    public Status(){

    }

    public Status(String date,String status,String userName){
        this.date = date;
        this.status = status;
        this.userName = userName;
    }


}
