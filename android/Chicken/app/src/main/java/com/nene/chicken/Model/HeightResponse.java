package com.nene.chicken.Model;

import java.util.List;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class HeightResponse {
    private List<Result> results;
    private String status;
    public class Result{
        double elevation;
        Position location;
        double resoultion;
    }

    public class Position{
        double lat;
        double lng;
    }


}
