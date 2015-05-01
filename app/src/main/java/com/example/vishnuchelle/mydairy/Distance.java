package com.example.vishnuchelle.mydairy;

/**
 * Created by Vishnu Chelle on 5/1/2015.
 */
public class Distance {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(String distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public Distance(String userName, String date, String distanceTravelled) {
        this.userName = userName;
        this.date = date;
        this.distanceTravelled = distanceTravelled;
    }

    public Distance(){

    }

    private String userName;
    private String date;
    private String distanceTravelled;



}
