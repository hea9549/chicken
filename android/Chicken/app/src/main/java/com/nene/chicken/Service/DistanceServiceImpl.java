package com.nene.chicken.Service;

import com.nene.chicken.Model.TransPosition;

import java.util.List;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class DistanceServiceImpl implements DistanceService {
    @Override
    public boolean canWalkingDistance(double startLat, double startLog, double destLat, double destLog) {
        // 거리가 유효한지 확인 8km이내여야함
        return false;
    }

    @Override
    public List<TransPosition> getPathList(TransPosition startPosition, TransPosition endPosition) {
        // 서버콜타야해서 비동기처리해야할듯
        return null;
    }
}
