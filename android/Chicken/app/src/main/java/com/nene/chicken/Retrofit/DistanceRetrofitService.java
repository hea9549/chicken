package com.nene.chicken.Retrofit;

import com.nene.chicken.Model.HeightResponse;
import com.nene.chicken.Model.TransPosition;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public interface DistanceRetrofitService {
    @GET
    Observable<List<TransPosition>> getPathList(@Url String url);

    @GET
    Observable<HeightResponse> getHeightObservable(@Url String url);
}
