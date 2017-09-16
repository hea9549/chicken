package com.nene.chicken.Service;

import com.nene.chicken.Model.HeightResponse;
import com.nene.chicken.Model.TransPosition;
import com.nene.chicken.Retrofit.DistanceRetrofitService;
import com.nene.chicken.Retrofit.RetrofitGenerator;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    public Observable<List<TransPosition>> getPathList(TransPosition startPosition, TransPosition endPosition) {
        return RetrofitGenerator.createService(DistanceRetrofitService.class)
                .getPathList(
                        "http://map.naver.com/findroute2/findWalkRoute.nhn?call=route2&output=json&coord_type=naver&search=0&start="
                                + startPosition.getLongitude() + "," + startPosition.getLatitude()
                                + "&destination="
                                + endPosition.getLongitude()+","+endPosition.getLatitude()
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HeightResponse> getHeight(TransPosition position) {
        return RetrofitGenerator.createService(DistanceRetrofitService.class)
                .getHeightObservable(
                        "http://maps.googleapis.com/maps/api/elevation/json?locations=" + position.getLatitude() + "," +
                                position.getLongitude() + "&sensor=false"
                )
                .doOnNext(result->{
                    if (result.getResults().size() == 0)return;
                        position.setHeight(result.getResults().get(0).getElevation());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

}
