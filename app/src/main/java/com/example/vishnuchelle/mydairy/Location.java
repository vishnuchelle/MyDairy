package com.example.vishnuchelle.mydairy;

/**
 * Created by Vishnu Chelle on 5/1/2015.
 */
public class Location {

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFormattedLocation() {
        return formattedLocation;
    }

    public void setFormattedLocation(String formattedLocation) {
        this.formattedLocation = formattedLocation;
    }

    public String userName;
    public String timeStamp;
    public String date;
    public String latitude;
    public String longitude;
    public String formattedLocation;

    public Location(){

    }

    public Location(String userName, String formattedLocation, String longitude, String latitude, String date, String timeStamp) {
        this.userName = userName;
        this.formattedLocation = formattedLocation;
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
        this.timeStamp = timeStamp;
    }
}
