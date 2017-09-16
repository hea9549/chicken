package com.nene.chicken.Model;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class TransPosition {
    private double latitude;
    private double longitude;
    private double height;

    public TransPosition(double latitude,double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public TransPosition(){

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
