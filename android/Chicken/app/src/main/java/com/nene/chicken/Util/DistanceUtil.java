package com.nene.chicken.Util;

import com.nene.chicken.Model.TransPosition;

import java.util.List;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class DistanceUtil {
    public static double getTotalDistance(List<TransPosition> positions) {
        double totalDistance = 0;
        if (positions == null) return -1;
        if (positions.size() == 0) return -1;
        for (int i = 0; i < positions.size() - 1; i++) {
            totalDistance +=
                    Math.sqrt(
                            Math.pow(Math.abs(positions.get(i).getLatitude() - positions.get(i + 1).getLatitude()), 2)
                                    + Math.pow(Math.abs(positions.get(i).getLongitude() - positions.get(i + 1).getLongitude()), 2)
                                    +Math.pow(Math.abs(positions.get(i).getHeight() - positions.get(i + 1).getHeight()), 2) );
        }
        return totalDistance;
    }


}
