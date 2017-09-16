package com.nene.chicken.Presentation.Presenter;

import com.nene.chicken.Service.DistanceService;
import com.nene.chicken.Service.DistanceServiceImpl;
import com.nene.chicken.Util.DistanceUtil;
import com.nhn.android.maps.maplib.NGeoPoint;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class MainPresenterImpl implements MainPresenter {
    private View view;
    private DistanceService distanceService;
    private double curSpeed;
    @Override
    public void setView(View view) {
        this.view = view;
        distanceService = new DistanceServiceImpl();
    }

    @Override
    public double getSpeed(NGeoPoint point){
        DistanceUtil.
        return
    }
}
