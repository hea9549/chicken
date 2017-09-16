package com.nene.chicken.Model;

import java.util.List;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class HeightResponse {
    private List<Result> results;
    private String status;
    public class Result{
        private double elevation;
        private Position location;
        private double resoultion;

        public double getElevation() {
            return elevation;
        }

        public void setElevation(double elevation) {
            this.elevation = elevation;
        }

        public Position getLocation() {
            return location;
        }

        public void setLocation(Position location) {
            this.location = location;
        }

        public double getResoultion() {
            return resoultion;
        }

        public void setResoultion(double resoultion) {
            this.resoultion = resoultion;
        }
    }

    public class Position{
        double lat;
        double lng;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
