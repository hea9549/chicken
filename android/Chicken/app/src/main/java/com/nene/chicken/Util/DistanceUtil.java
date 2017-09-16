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

    public static double calDistance(TransPosition p1, TransPosition p2){
        double lat1 = p1.getLatitude(),lon1 = p1.getLongitude(),lat2=p2.getLatitude(),lon2=p2.getLatitude();
        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
        dist = dist * 1000.0;      // 단위  km 에서 m 로 변환

        return Math.sqrt(Math.pow(dist,2)+Math.pow(Math.abs(p1.getHeight()-p2.getHeight()),2));
    }

    // 주어진 도(degree) 값을 라디언으로 변환
    private static double deg2rad(double deg){
        return (double)(deg * Math.PI / (double)180d);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private static double rad2deg(double rad){
        return (double)(rad * (double)180d / Math.PI);
    }

}
