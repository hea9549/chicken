package com.nene.chicken.Presentation.Presenter;

import com.nene.chicken.Model.TransPosition;
import com.nene.chicken.Service.DistanceService;
import com.nene.chicken.Service.DistanceServiceImpl;
import com.nene.chicken.Util.DistanceUtil;
import com.nhn.android.maps.maplib.NGeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class MainMapPresenterImpl implements MainMapPresenter {
    private View view;
    private DistanceService distanceService;
    private List<TransPosition> savePositions;
    private double curSpeed;
    public MainMapPresenterImpl(){
        distanceService = new DistanceServiceImpl();
        savePositions = new ArrayList<>();
    }
    @Override
    public void setView(View view) {
        this.view = view;
    }


}
